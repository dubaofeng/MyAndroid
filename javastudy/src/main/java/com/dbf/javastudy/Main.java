package com.dbf.javastudy;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("main");
//        maoPaoSort(array);
//        jianDanXuanZeSort(array);
//        jianDanInsertSort(array);
//        ShellSort(array);
//        sort(array);
//        mergeSort(array);
//        int[] test = quickSort(array, 0, array.length - 1);
        int[] test = heapSort(array);
        for (int i = 0; i < test.length; i++) {
            System.out.println("test=" + test[i]);
        }
    }

    public static final int[] array = {10, 4, 6, 1, 0, 2, 9, 3, 5, 8, 7};

    //冒泡排序
    public static int[] maoPaoSort(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array==null");
        }
        if (array.length <= 1) {
            return array;
        }
        int tempSize = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    tempSize++;
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        System.out.println("交换次数=" + tempSize);
        for (int digtal : array) {
            System.out.println(digtal);
        }
        return array;
    }

    //简单选择排序
    public static int[] jianDanXuanZeSort(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array==null");
        }
        if (array.length <= 1) {
            return array;
        }
        int tempSize = 0;
        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            tempSize++;
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
        System.out.println("交换次数=" + tempSize);
        for (int digtal : array) {
            System.out.println(digtal);
        }
        return array;
    }

    //简单插入排序
    public static int[] jianDanInsertSort(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array==null");
        }
        if (array.length <= 1) {
            return array;
        }
        int tempSize = 0;
        int currentValue;
        for (int i = 0; i < array.length - 1; i++) {
            int preIndex = i;
            currentValue = array[preIndex + 1];
            while (preIndex >= 0 && currentValue < array[preIndex]) {
                //按照升序，后面的小，就往后移一个位置，preIndex-- 依次与前一个值对比，比自己大的都往后移，因为自己的位置空出来了，直到没有比自己大的了，就跳出了循环
                array[preIndex + 1] = array[preIndex];
                preIndex--;
                tempSize++;
            }
            tempSize++;
            //跳出循环后preIndex已经在preIndex + 1的地方，因为跳出循环之前preIndex--，
            array[preIndex + 1] = currentValue;
        }
        System.out.println("交换次数=" + tempSize);
        for (int digtal : array) {
            System.out.println(digtal);
        }
        return array;
    }

    //希尔排序
    public static int[] ShellSort(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array==null");
        }
        if (array.length <= 1) {
            return array;
        }
        int tempSize = 0;
        int len = array.length;
        int gap = len / 2;
        int currentValue;
        while (gap > 0) {
            for (int i = gap; i < len; i = i + gap) {
                int preIndex = i - gap;
                currentValue = array[i];
                while (preIndex >= 0 && currentValue < array[preIndex]) {
                    System.out.println("preIndex=" + preIndex + "-----------gap=" + gap);
                    array[preIndex + gap] = array[preIndex];
                    preIndex = preIndex - gap;
                }
                array[preIndex + gap] = currentValue;
            }

            gap = gap / 2;
        }
        System.out.println("交换次数=" + tempSize);
        for (int digtal : array) {
            System.out.println(digtal);
        }
        return array;
    }

    //归并排序
    public static int[] mergeSort(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array==null");
        }
        if ((array.length < 2)) {
            return array;
        }
        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        int[] resultleft = mergeSort(left);
        String resultleftStr = "";
        for (int i = 0; i < resultleft.length; i++) {
            resultleftStr += resultleft[i] + "-";
        }
        System.out.println("---------------resultleftStr=----" + resultleftStr);
        int[] resultright = mergeSort(right);
        String resultrightStr = "";
        for (int i = 0; i < resultright.length; i++) {
            resultrightStr += resultright[i] + "-";
        }
        System.out.println("---------------resultrightStr=----" + resultrightStr);
        return merge(resultleft, resultright);
    }

    private static int[] merge(int[] left, int[] right) {
        String leftsrt = "";
        for (int i = 0; i < left.length; i++) {
            leftsrt += left[i] + "-";
        }
        System.out.println("---------------left数组----" + leftsrt);
        String rightsrt = "";
        for (int i = 0; i < right.length; i++) {
            rightsrt += right[i] + "-";
        }
        System.out.println("---------------right数组----" + rightsrt);
        System.out.println("---------------华丽分割线--------------------");
        int[] result = new int[left.length + right.length];
        for (int index = 0, leftIndex = 0, rightIndex = 0; index < result.length; index++) {
            if (leftIndex >= left.length) {
                //左边数组已经全部按顺序放到result，剩余的只要把右边的按顺序放入result就行，不用担心右边的排序不对，因为左右两边各自的排序是正确的
                result[index] = right[rightIndex++];
            } else if (rightIndex >= right.length) {
                //同上
                result[index] = left[leftIndex++];
            } else if (left[leftIndex] > right[rightIndex]) {
                //按升序排列，小的放入result，下标增加
                result[index] = right[rightIndex++];
            } else {
                //按升序排列，小的放入result，下标增加
                result[index] = left[leftIndex++];
            }
        }
//        System.out.println("---------------合并的数组--------------------");
//        for (int i = 0; i < result.length; i++) {
//            System.out.println("result=" + result[i]);
//        }
        //返回排序好的数组
        return result;
    }

    //快速排序
    private static int[] quickSort(int[] array, int start, int end) {
        if (array.length < 1 || start < 0 || end > array.length || start > end) {
            return null;
        }
        int zoneIndex = partition(array, start, end);
        if (zoneIndex > start) {
            quickSort(array, start, zoneIndex - 1);
        }
        if (zoneIndex < end) {
            quickSort(array, zoneIndex + 1, end);
        }
        return array;
    }

    private static int partition(int[] array, int start, int end) {
        int pivot = (int) (start + Math.random() * (end - start + 1));//随机基准数的下标
        int zoneIndex = start - 1;
        swap(array, pivot, array[end]);
        for (int i = start; i <= end; i++) {
            if (array[i] <= array[end]) {
                //按升序，如果当前数小于等于基准数，指示器右移
                zoneIndex++;
                if (zoneIndex < i) {
                    //右移指示器后，指示器又小于当前遍历数的下标，说明指示器对应的数值是大于基准数的，需要与当前遍历数交换
                    swap(array, zoneIndex, i);
                }
            }
        }
        return zoneIndex;
    }

    static int len = 0;

    //堆排序
    private static int[] heapSort(int[] array) {
        len = array.length;
        if ((len < 1)) {
            return array;
        }
        //升序排序，构建最大堆
        buildMaxHeap(array);
        //最大堆构建完成，堆顶元素和末尾元素交换，len-- 最大元素就被排序出来，重复此操作，得出排序
        while (len > 0) {
            swap(array, 0, len - 1);
            len--;
            adjustHeap(array, 0);
        }

        return array;
    }

    private static void buildMaxHeap(int[] array) {
        //从最后一个非叶节点向前遍历
        for (int i = (len / 2 - 1); i >= 0; i--) {
            adjustHeap(array, i);
        }
    }

    private static void adjustHeap(int[] array, int i) {
        //以当前节点的值为最大值
        int maxIndex = i;
        //左节点
        int leftIndex = 2 * i + 1;
        //右节点
        int rightIndex = 2 * (i + 1);
        if ((leftIndex < len && array[leftIndex] > array[maxIndex])) {
            //左节点存在并且左节点大于当前节点，更新最大值节点下标
            maxIndex = leftIndex;
        }
        if ((rightIndex < len && array[rightIndex] > array[maxIndex])) {
            //右节点
            maxIndex = rightIndex;
        }
        if (maxIndex != i) {
            //最大值节点不是非叶节点，交换值
            swap(array, i, maxIndex);
            //交换值后有可能不是一个最大完全二叉堆，递归调用自己,如从下往上，父节点被替换后，不满足最大堆，父节点的左右节点再来一次调整，完了之后子节点被调整后，其与其子节点又不符合最大堆，一直递归到没有子节点，结束后符合最大堆
            adjustHeap(array, maxIndex);
        }
    }

    private static void swap(int[] array, int i, int j) {
        int tamp = array[i];
        array[i] = array[j];
        array[j] = tamp;
    }
}
