package org.communication.Exception;


import org.common.exception.BaseException;
import org.communication.dto.TemplateMastDto;
import org.springframework.http.HttpStatus;

public class TemplateMastException extends BaseException {

    private TemplateMastDto rData;

    public TemplateMastException(int rCode, HttpStatus rStatus, String displayMessage, String rMsg, TemplateMastDto rData) {
        super(rCode, rStatus, displayMessage, rMsg);
        this.rData = rData;
    }
}
