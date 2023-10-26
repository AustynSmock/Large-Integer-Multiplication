public class LargeIntMult{
    /*
     * Authors: Nathan Lancaster, Austyn Smock
     * Buff IDs: 1050080, 1050128
     * Work split: 50/50
     * Austyn: divU, modU, multU, isZero, test cases (main)
     * Nathan: addU, prodUV, printU, debugging
     */
    public static int[] divU(int[] u, int m){
        // will return as an array the result of u/10^m
        if (isZero(u) || u.length <= m){
            return new int[1];
        }
        int x = u.length - m;
        int[] quotient = new int[x];
        for (int i = 0 ; i < x ; i++){
            quotient[i] = u[i];
        }
        return quotient;
    }

    public static int[] modU(int[] u, int m){
        // will return as an array the result of u%10^m
        if (isZero(u)) return new int[1];
        if (u.length <= m) return u;
        int[] remainder = new int[m];
        for(int i = 0; i<m; i++){
            remainder[remainder.length-i-1] = u[u.length-i-1];
        }
        return remainder;
    }

    public static int[] multU(int[] u, int m){
        // will return as an array the result of u*10^m
        assert(m>0);
        int x = u.length + m;
        int[] product = new int[x];
        for (int i = 0 ; i < u.length ; i++){
            product[i] = u[i];
        }
        for (int i = u.length; i<x; i++){
            product[i] = 0;
        }
        return product;
    }

    public static int[] addU(int[] u, int[] v){
        // will return the result of adding array u and array v of integers
        int usize = u.length;
        int vsize = v.length;
        int maxSize = Math.max(usize, vsize);
        int minSize = Math.min(usize, vsize);
        int ssize = maxSize+1;
        
        int[] sum = new int[maxSize+1];
        for(int i = 0; i < minSize; i++){
            int add = u[usize-1] + v[vsize-1] + sum[ssize-1];
            if ( add > 9){
                sum[ssize-2] = 1;
                int num = add - 10;
                sum[ssize-1] = num;
            } else {sum[ssize-1] = add;}
            usize--;
            vsize--;
            ssize--;
        }
        if (u.length == minSize && minSize != maxSize){
            for (int i = minSize; i < maxSize; i++){
                int add = v[vsize-1] + sum[ssize-1];
                if ( add > 9){
                    sum[ssize-2] = 1;
                    int num = add - 10;
                    sum[ssize-1] = num;
                } else {sum[ssize-1] = add;}
                vsize--;
                ssize--;
            }
        } 
        if (v.length == minSize && minSize != maxSize) {
            for (int i = minSize; i < maxSize; i++){
                int add = u[usize-1] + sum[ssize-1];
                if ( add > 9){
                    sum[ssize-2] = 1;
                    int num = add - 10;
                    sum[ssize-1] = num;
                } else {sum[ssize-1] = add;}
                usize--;
                ssize--;
            }
        }
        int[] newSum = new int[sum.length-1];
        while (!isZero(sum) && sum[0] == 0){
            newSum = new int[sum.length-1];
            for (int i = 0; i < sum.length-1; i++){
                newSum[i] = sum[i+1];
            }
            sum = newSum;
        }
        return sum;
    }

    public static void printU(int[] u){
        // will display the array of integer as a number
        int len = u.length;
        int seperatorCheck = len%3;
        for (int i = 0; i < u.length; i++){
            if (seperatorCheck == 0 && len != 0 && len != u.length){
                System.out.print(",");
            }
            System.out.print(u[i]);
            seperatorCheck = --len%3 ;
        }
    }

    public static int[] prodUV(int[] u, int[] v){
        // will return the product u*v as an array, u and v are arrays
        int n, m;

        n = Math.max(u.length, v.length);
        if (isZero(u) || isZero(v)) {
            return new int[1];
        }
        if (u.length == 1 && v.length == 1) {
            int[] y;
            if (u[0] * v[0] >= 10) {
                y = new int[2];
                y[0] = (u[0] * v[0])/10;
                y[1] = (u[0] * v[0])%10;
            } else {
                y = new int[1];
                y[0] = u[0] * v[0];
            }
            return y;
        } else {
            n = Math.max(u.length, v.length);
            m = n/2;
            
            int[] uCal1 = divU(u, m);
            int[] uCal2 = modU(u, m);
            int[] vCal1 = divU(v, m);
            int[] vCal2 = modU(v, m);
            int[] eCal1 = prodUV(uCal1,vCal1);
            int[] eCal2 = prodUV(uCal1,vCal2);
            int[] eCal3 = prodUV(vCal1,uCal2);
            int[] eCal4 = prodUV(uCal2,vCal2);
            int[] eCal5 = multU(eCal1,2*m);
            int[] eCal6 = addU(eCal2,eCal3);
            int[] eCal7 = multU(eCal6,m);
            int[] eCal8 = addU(eCal5,eCal7);

            return addU(eCal4,eCal8); 
        }
    }

    public static boolean isZero(int[] u){
        // will return true if the array u is all zeroes and false otherwise. The last function is optional, 
        // but it will be useful to have one because you will need this many times in the other implementations.
        for (int i = 0; i<u.length; i++){
            if (u[i] != 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args){
        int[] u = {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0};

        int[] v1 = {1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0};
        int[] a1 = prodUV(u, v1);
        printU(a1);
        System.out.println("");

        int[] v2 = {1,2,3};
        int[] a2 = prodUV(u, v2);
        printU(a2);
        System.out.println("");
    }
}