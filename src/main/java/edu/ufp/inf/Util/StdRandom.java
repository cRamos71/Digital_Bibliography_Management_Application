package edu.ufp.inf.Util;

import java.io.Serializable;
import java.util.Random;

public final class StdRandom implements Serializable {
    private static Random random;
    private static long seed = System.currentTimeMillis();

    private StdRandom() {
    }

    public static void setSeed(long s) {
        seed = s;
        random = new Random(seed);
    }

    public static long getSeed() {
        return seed;
    }

    /** @deprecated */
    @Deprecated
    public static double uniform() {
        return uniformDouble();
    }

    public static double uniformDouble() {
        return random.nextDouble();
    }

    /** @deprecated */
    @Deprecated
    public static int uniform(int n) {
        return uniformInt(n);
    }

    public static int uniformInt(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("argument must be positive: " + n);
        } else {
            return random.nextInt(n);
        }
    }

    /** @deprecated */
    @Deprecated
    public static long uniform(long n) {
        return uniformLong(n);
    }

    public static long uniformLong(long n) {
        if (n <= 0L) {
            throw new IllegalArgumentException("argument must be positive: " + n);
        } else {
            long r = random.nextLong();
            long m = n - 1L;
            if ((n & m) == 0L) {
                return r & m;
            } else {
                for(long u = r >>> 1; u + m - (r = u % n) < 0L; u = random.nextLong() >>> 1) {
                }

                return r;
            }
        }
    }

    /** @deprecated */
    @Deprecated
    public static double random() {
        return uniformDouble();
    }

    /** @deprecated */
    @Deprecated
    public static int uniform(int a, int b) {
        return uniformInt(a, b);
    }

    public static int uniformInt(int a, int b) {
        if (b > a && (long)b - (long)a < 2147483647L) {
            return a + uniform(b - a);
        } else {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }
    }

    /** @deprecated */
    @Deprecated
    public static double uniform(double a, double b) {
        return uniformDouble(a, b);
    }

    public static double uniformDouble(double a, double b) {
        if (!(a < b)) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        } else {
            return a + uniform() * (b - a);
        }
    }

    public static boolean bernoulli(double p) {
        if (p >= 0.0 && p <= 1.0) {
            return uniformDouble() < p;
        } else {
            throw new IllegalArgumentException("probability p must be between 0.0 and 1.0: " + p);
        }
    }

    public static boolean bernoulli() {
        return bernoulli(0.5);
    }

    public static double gaussian() {
        double r;
        double x;
        do {
            x = uniformDouble(-1.0, 1.0);
            double y = uniformDouble(-1.0, 1.0);
            r = x * x + y * y;
        } while(r >= 1.0 || r == 0.0);

        return x * Math.sqrt(-2.0 * Math.log(r) / r);
    }

    public static double gaussian(double mu, double sigma) {
        return mu + sigma * gaussian();
    }

    public static int geometric(double p) {
        if (!(p >= 0.0)) {
            throw new IllegalArgumentException("probability p must be greater than 0: " + p);
        } else if (!(p <= 1.0)) {
            throw new IllegalArgumentException("probability p must not be larger than 1: " + p);
        } else {
            return (int)Math.ceil(Math.log(uniformDouble()) / Math.log(1.0 - p));
        }
    }

    public static int poisson(double lambda) {
        if (!(lambda > 0.0)) {
            throw new IllegalArgumentException("lambda must be positive: " + lambda);
        } else if (Double.isInfinite(lambda)) {
            throw new IllegalArgumentException("lambda must not be infinite: " + lambda);
        } else {
            int k = 0;
            double p = 1.0;
            double expLambda = Math.exp(-lambda);

            do {
                ++k;
                p *= uniformDouble();
            } while(p >= expLambda);

            return k - 1;
        }
    }

    public static double pareto() {
        return pareto(1.0);
    }

    public static double pareto(double alpha) {
        if (!(alpha > 0.0)) {
            throw new IllegalArgumentException("alpha must be positive: " + alpha);
        } else {
            return Math.pow(1.0 - uniformDouble(), -1.0 / alpha) - 1.0;
        }
    }

    public static double cauchy() {
        return Math.tan(Math.PI * (uniformDouble() - 0.5));
    }

    public static int discrete(double[] probabilities) {
        if (probabilities == null) {
            throw new IllegalArgumentException("argument array must not be null");
        } else {
            double EPSILON = 1.0E-14;
            double sum = 0.0;

            for(int i = 0; i < probabilities.length; ++i) {
                if (!(probabilities[i] >= 0.0)) {
                    throw new IllegalArgumentException("array entry " + i + " must be non-negative: " + probabilities[i]);
                }

                sum += probabilities[i];
            }

            if (!(sum > 1.0 + EPSILON) && !(sum < 1.0 - EPSILON)) {
                while(true) {
                    double r = uniformDouble();
                    sum = 0.0;

                    for(int i = 0; i < probabilities.length; ++i) {
                        sum += probabilities[i];
                        if (sum > r) {
                            return i;
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException("sum of array entries does not approximately equal 1.0: " + sum);
            }
        }
    }

    public static int discrete(int[] frequencies) {
        if (frequencies == null) {
            throw new IllegalArgumentException("argument array must not be null");
        } else {
            long sum = 0L;

            for(int i = 0; i < frequencies.length; ++i) {
                if (frequencies[i] < 0) {
                    throw new IllegalArgumentException("array entry " + i + " must be non-negative: " + frequencies[i]);
                }

                sum += (long)frequencies[i];
            }

            if (sum == 0L) {
                throw new IllegalArgumentException("at least one array entry must be positive");
            } else if (sum >= 2147483647L) {
                throw new IllegalArgumentException("sum of frequencies overflows an int");
            } else {
                double r = (double)uniformInt((int)sum);
                sum = 0L;

                for(int i = 0; i < frequencies.length; ++i) {
                    sum += (long)frequencies[i];
                    if ((double)sum > r) {
                        return i;
                    }
                }

                assert false;

                return -1;
            }
        }
    }

    public static double exponential(double lambda) {
        if (!(lambda > 0.0)) {
            throw new IllegalArgumentException("lambda must be positive: " + lambda);
        } else {
            return -Math.log(1.0 - uniformDouble()) / lambda;
        }
    }

    /** @deprecated */
    @Deprecated
    public static double exp(double lambda) {
        return exponential(lambda);
    }

    public static void shuffle(Object[] a) {
        validateNotNull(a);
        int n = a.length;

        for(int i = 0; i < n; ++i) {
            int r = i + uniformInt(n - i);
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }

    }

    public static void shuffle(double[] a) {
        validateNotNull(a);
        int n = a.length;

        for(int i = 0; i < n; ++i) {
            int r = i + uniformInt(n - i);
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }

    }

    public static void shuffle(int[] a) {
        validateNotNull(a);
        int n = a.length;

        for(int i = 0; i < n; ++i) {
            int r = i + uniformInt(n - i);
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }

    }

    public static void shuffle(char[] a) {
        validateNotNull(a);
        int n = a.length;

        for(int i = 0; i < n; ++i) {
            int r = i + uniformInt(n - i);
            char temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }

    }

    public static void shuffle(Object[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        for(int i = lo; i < hi; ++i) {
            int r = i + uniformInt(hi - i);
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }

    }

    public static void shuffle(double[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        for(int i = lo; i < hi; ++i) {
            int r = i + uniformInt(hi - i);
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }

    }

    public static void shuffle(int[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        for(int i = lo; i < hi; ++i) {
            int r = i + uniformInt(hi - i);
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }

    }

    public static int[] permutation(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative: " + n);
        } else {
            int[] perm = new int[n];

            for(int i = 0; i < n; perm[i] = i++) {
            }

            shuffle(perm);
            return perm;
        }
    }

    public static int[] permutation(int n, int k) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative: " + n);
        } else if (k >= 0 && k <= n) {
            int[] perm = new int[k];

            int i;
            int r;
            for(i = 0; i < k; perm[r] = i++) {
                r = uniformInt(i + 1);
                perm[i] = perm[r];
            }

            for(i = k; i < n; ++i) {
                r = uniformInt(i + 1);
                if (r < k) {
                    perm[r] = i;
                }
            }

            return perm;
        } else {
            throw new IllegalArgumentException("k must be between 0 and n: " + k);
        }
    }

    private static void validateNotNull(Object x) {
        if (x == null) {
            throw new IllegalArgumentException("argument must not be null");
        }
    }

    private static void validateSubarrayIndices(int lo, int hi, int length) {
        if (lo < 0 || hi > length || lo > hi) {
            throw new IllegalArgumentException("subarray indices out of bounds: [" + lo + ", " + hi + ")");
        }
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        if (args.length == 2) {
            setSeed(Long.parseLong(args[1]));
        }

        double[] probabilities = new double[]{0.5, 0.3, 0.1, 0.1};
        int[] frequencies = new int[]{5, 3, 1, 1};
        String[] a = "A B C D E F G".split(" ");
        StdOut.println("seed = " + getSeed());

        for(int i = 0; i < n; ++i) {
            StdOut.printf("%2d ", new Object[]{uniformInt(100)});
            StdOut.printf("%8.5f ", new Object[]{uniformDouble(10.0, 99.0)});
            StdOut.printf("%5b ", new Object[]{bernoulli(0.5)});
            StdOut.printf("%7.5f ", new Object[]{gaussian(9.0, 0.2)});
            StdOut.printf("%1d ", new Object[]{discrete(probabilities)});
            StdOut.printf("%1d ", new Object[]{discrete(frequencies)});
            StdOut.printf("%11d ", new Object[]{uniformLong(100000000000L)});
            shuffle((Object[])a);
            String[] var6 = a;
            int var7 = a.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                String s = var6[var8];
                StdOut.print(s);
            }

            StdOut.println();
        }

    }

    static {
        random = new Random(seed);
    }
}
