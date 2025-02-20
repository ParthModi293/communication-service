package com.clapcle.communication.common;

public class ConstCommunication {

    public static final class PatternCheck {

        public static final String AlphaNumericSpace = "^[A-Za-z0-9 ]*$";
        public static final String SearchText = "^[A-Za-z0-9-_ ]*$";
        public static final String EMAIL = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$";
        public static final String REMARK = "^([A-Za-z0-9., ]{1,255})?$";
        public static final String SIZE45 = "^.{1,45}$";
        public static final String URL = "^[A-Za-z0-9-/._:]*$";
    }

}
