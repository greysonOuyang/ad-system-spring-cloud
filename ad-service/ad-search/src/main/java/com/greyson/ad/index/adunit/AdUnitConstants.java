package com.greyson.ad.index.adunit;

/**
 * @author greyson
 * @time 2020/2/12 15:01
 */

public class AdUnitConstants {
    public static class POSITION_TYPE {
        // 二进制编排可以使用位或/位与运算加快检索速度
        public static final int KAIPING = 1;
        public static final int TIEPIAN = 2;
        public static final int TIEPIAN_MIDDLE = 4;
        public static final int TIEPIAN_PAUSE = 8;
        public static final int TIEPIAN_POST = 16;
    }
}
