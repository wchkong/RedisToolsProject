package com.wch.redisTest;

import java.util.concurrent.ThreadLocalRandom;

/**
 * HyperLogLog 实现误差范围内计数的原理测试
 *
 * 注释的代码中分了 1024 个桶，计算平均数使用了调和平均 (倒数的平均)。
 * 普通的平均法可能因为个别离群值对平均结果产生较大的影响，调和平均可以有效平滑离群值的影响。
 *
 */
public class PfTest {
    static class  BitKeeper {
        private int maxbits;

        public void random(long value) {
            //long value = ThreadLocalRandom.current().nextLong(2L << 32);
            int bits = lowZeros(value);
            if (bits > this.maxbits) {
                this.maxbits = bits;
            }
        }

        private int lowZeros(long value) {
            int i = 1;
            for (; i < 32; i++) {
                if (value >> i << i != value) {
                    break;
                }
            }
            return i - 1;
        }
    }

    static class Experiment {
        private int n;
        private int k;
        private BitKeeper[] keepers;

        public Experiment(int n) {
            this(n, 1024);
        }
        public Experiment(int n, int k) {
            this.keepers = new BitKeeper[k];
            this.n = n;
            this.k = k;
            for (int i = 0; i < k; i++) {
                this.keepers[i] = new BitKeeper();
            }
        }
        public void work() {
            for (int i = 0; i < this.n; i++) {
                long m = ThreadLocalRandom.current().nextLong(1L << 32);
                BitKeeper keeper = keepers[(int) (((m & 0xfff000) >> 16) % keepers.length)];
                keeper.random(m);
            }
        }

        public double estimate() {
            double sumbitsInverse = 0.0;
            for (BitKeeper bitKeeper:
                 keepers) {
                sumbitsInverse += 1.0 / bitKeeper.maxbits;
            }
            double avgBits = keepers.length / sumbitsInverse;
            return Math.pow(2, avgBits) * this.k;
        }

        public void debug() {
            //System.out.printf("%d %.2f %d\n", this.n, Math.log(this.n) / Math.log(2), this.keeper.maxbits);
        }
    }

    public static void main(String[] q) {
        for (int i = 1000; i < 100000; i+=100) {
            Experiment experiment = new Experiment(i);
            experiment.work();
            double estimate = experiment.estimate();
            System.out.printf("%d %.2f %.2f\n", i, estimate, Math.abs(estimate - i) / i);
        }
    }

}
