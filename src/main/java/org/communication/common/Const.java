package org.communication.common;

public class Const {

    public static final class PatternCheck {

        public static final String AlphaNumericSpace = "^[A-Za-z0-9 ]*$";
        public static final String SearchText = "^[A-Za-z0-9-_ ]*$";
        public static final String EMAIL = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$";
        public static final String REMARK = "^([A-Za-z0-9., ]{1,255})?$";
    }

}
