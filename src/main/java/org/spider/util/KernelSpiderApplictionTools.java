package org.spider.util;

import org.spider.core.KernelSpider;

/**
 * @author liuxin
 * @version Id: KernelSpiderApplictionTools.java, v 0.1 2018/8/1 下午10:04
 */
public class KernelSpiderApplictionTools {
    private static KernelSpider kernelSpider;

    public static void setKernelSpider(KernelSpider kernelSpider) {
        KernelSpiderApplictionTools.kernelSpider = kernelSpider;
    }

    public static KernelSpider getKernelSpider() {
        return kernelSpider;
    }
}
