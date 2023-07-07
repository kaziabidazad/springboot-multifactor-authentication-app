package com.duke.mfa.poc.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.duke.mfa.poc.component.FileDownloadManager;
import com.duke.mfa.poc.dto.Password;
import com.duke.mfa.poc.dto.TotpRegistrationResponseDto;
import com.duke.mfa.poc.dto.UserRegistrationResponseDto;
import com.duke.mfa.poc.dto.UserRequestDto;
import com.duke.mfa.poc.entity.User;
import com.duke.mfa.poc.repo.UserRepository;
import com.duke.mfa.poc.utils.Constants;
import com.duke.mfa.poc.utils.PasswordUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Kazi
 */
@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileDownloadManager downloadManager;

    /**
     * Adds a new user entry into the database
     * 
     * @param request
     * @return
     */
    @Transactional
    public UserRegistrationResponseDto registerUser(UserRequestDto request) {
        UserRegistrationResponseDto response = null;
        if (request != null) {
            User user = new User();
            user.setEmailId(request.getEmail());
            user.setName(request.getName());
            user.setUsername(request.getUsername());
            // Generate hash and salt
            Password password = PasswordUtils.convertToHashedPassword(request.getPassword());
            user.setHash(password.getHash());
            user.setSalt(password.getSalt());
            // Generate a Totp secret
            char[] totpSecret = PasswordUtils.generatePassword(64, true);
            LOGGER.info("TOTP Secret : " + Arrays.toString(totpSecret));
            user.setTotpSecret(totpSecret);
            userRepository.save(user);
            response = new UserRegistrationResponseDto("Registered Successfully.", user.getUserId(), user.getName());
        }
        return response;
    }

    @Transactional
    public TotpRegistrationResponseDto markTotpRegistered(Integer userId) {
        TotpRegistrationResponseDto response = null;
        String message = null;
        if (userId != null) {
            Optional<User> userObj = userRepository.findById(userId);
            if (userObj.isEmpty())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user.");
            User user = userObj.isPresent() ? userObj.get() : null;
            user.setMfaRegistered(true);
            userRepository.save(user);
            message = "MFA Registered Successfully. Navigate to MFA page and proceed.";
        } else {
            message = "Invalid User";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
        return response;
    }

    public void createAndDownloadQR(HttpServletResponse response, Integer userId) {
        // Generate the url for QR
        String totpUrl = generateTotpUrlForUser(userId);
        LOGGER.info("TOTP URL: " + totpUrl);
        // Generate the QR image
        // Create a temp file
        File tempQrFile = new File("temp_qr_" + System.currentTimeMillis() + ".png");
        generateQR(tempQrFile, totpUrl);
        try {
            downloadManager.downloadFile(response, tempQrFile, true);
        } catch (IOException e) {
            LOGGER.error("Error while downloading QR code: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to generate QR Code!");
        } finally {
            try {
                Files.deleteIfExists(tempQrFile.toPath());
            } catch (IOException e) {
                LOGGER.error("Unable to delete temp qr file : " + tempQrFile.getAbsolutePath(), e);
            }
        }

    }

    public String generateTotpUrlForUser(Integer userId) {
        Optional<User> userObj = userRepository.findById(userId);
        if (userObj.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user.");
        User user = userObj.isPresent() ? userObj.get() : null;
        char[] totpSecret = user.getTotpSecret();
        // Generate the totp url
        String totpUrl = String.format(Constants.TOTP_URL_TEMPLATE, Constants.APP_NAME, user.getEmailId(),
               PasswordUtils.getBase32TotpSecret(totpSecret), Constants.APP_NAME);
        return totpUrl;
    }

    public static void generateQR(File file, String code) {
        try {
            file.createNewFile();
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(code, BarcodeFormat.QR_CODE, 500, 500);
            // Colour : 1 byte of alpha and 3 bytes of rgb
            // 0xFF - Alpha and the rest is rgb
            int bgColour = 0xFFff006e;
            int foregroundColour = 0xFFffafcc;
            MatrixToImageConfig imageConfig = new MatrixToImageConfig(foregroundColour, bgColour);
            // Creating BnW QR,
//            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix, imageConfig);

            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            LOGGER.error("Error while creating QR code temp file: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to generate QR Code!");
        } catch (WriterException e) {
            LOGGER.error("Error while generating QR code: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to generate QR Code!");
        }
    }

//    public static void main(String[] args) throws IOException {
//        File file = new File("d:/stuffs/Desktop/qr001.png");
//        ByteArrayOutputStream stream = QRCode.from(
//                "otpauth://totp/MFA APP:testuser001@email.com?secret=12aBohZ16b9WB87BdDa49xEE982c2yyA62ml9509q36dAg64SJ79j7Q5UQ914H32&issuer=MFA APP")
//                .withSize(500, 500).withColor(0xFFff006e, 0xFFffafcc).stream();
//        ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());
//        ImageIO.write(ImageIO.read(bis), "png", file);
////        generateQR(file,
////                "otpauth://totp/MFA APP:testuser001@email.com?secret=12aBohZ16b9WB87BdDa49xEE982c2yyA62ml9509q36dAg64SJ79j7Q5UQ914H32&issuer=MFA APP");
//    }
}
