package com.duke.mfa.poc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Kazi
 */
@JsonInclude(content = Include.ALWAYS)
public interface CommonResponseDto extends CommonDto {

}
