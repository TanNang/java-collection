package com.zfl9.collection;

import java.util.Random;
import java.util.Comparator;
import java.lang.reflect.Array;

public class ArrayAlgorithm {
    /* 线性查找 */
    public static <T>
        int linearSearch(T[] array, int from, int to, T key)
    {
        for (int i = from; i < to; i++)
            if (key == null ? key == array[i] : key.equals(array[i]))
                return i;
        return -1;
    }
    public static <T>
        int linearSearch(T[] array, T key)
    {
        return linearSearch(array, 0, array.length, key);
    }

    /* 二分查找(自然排序) */
    public static <T extends Comparable<? super T>>
        int binarySearch(T[] array, int from, int to, T key)
    {
        int low = from;
        int high = to - 1;
        int mid;
        int cmp;

        while (low <= high) {
            mid = (low + high) / 2;
            cmp = key.compareTo(array[mid]);

            if (cmp == 0)
                return mid;
            else if (cmp < 0)
                high = mid - 1;
            else
                low = mid + 1;
        }

        return -1;
    }
    public static <T extends Comparable<? super T>>
        int binarySearch(T[] array, T key)
    {
        return binarySearch(array, 0, array.length, key);
    }

    /* 二分查找(自定义排序) */
    public static <T>
        int binarySearch(T[] array, int from, int to, T key, Comparator<? super T> comp)
    {
        int low = from;
        int high = to - 1;
        int mid;
        int cmp;

        while (low <= high) {
            mid = (low + high) / 2;
            cmp = comp.compare(key, array[mid]);

            if (cmp == 0)
                return mid;
            else if (cmp < 0)
                high = mid - 1;
            else
                low = mid + 1;
        }

        return -1;
    }
    public static <T>
        int binarySearch(T[] array, T key, Comparator<? super T> comp)
    {
        return binarySearch(array, 0, array.length, key, comp);
    }

    /* 插入排序(自然排序) */
    public static <T extends Comparable<? super T>>
        T[] insertSort(T[] array, int from, int to)
    {
        T curVal;
        for (int i = from + 1, j; i < to; i++) {
            curVal = array[i];
            for (j = i - 1; j >= from && array[j].compareTo(curVal) > 0; j--)
                array[j + 1] = array[j];
            array[j + 1] = curVal;
        }
        return array;
    }
    public static <T extends Comparable<? super T>>
        T[] insertSort(T[] array)
    {
        return insertSort(array, 0, array.length);
    }

    /* 插入排序(自定义排序) */
    public static <T>
        T[] insertSort(T[] array, int from, int to, Comparator<? super T> comp)
    {
        T curVal;
        for (int i = from + 1, j; i < to; i++) {
            curVal = array[i];
            for (j = i - 1; j >= from && comp.compare(array[j], curVal) > 0; j--)
                array[j + 1] = array[j];
            array[j + 1] = curVal;
        }
        return array;
    }
    public static <T>
        T[] insertSort(T[] array, Comparator<? super T> comp)
    {
        return insertSort(array, 0, array.length, comp);
    }

    /* 希尔排序(自然排序) */
    public static <T extends Comparable<? super T>>
        T[] shellSort(T[] array, int from, int to)
    {
        T curVal; int i, j;
        for (int gap = (to - from) / 2; gap > 0; gap /= 2) { // 步长
            for (i = gap + from; i < to; i++) { // 插入排序
                curVal = array[i];
                for (j = i - gap; j >= from && array[j].compareTo(curVal) > 0; j -= gap)
                    array[j + gap] = array[j];
                array[j + gap] = curVal;
            }
        }
        return array;
    }
    public static <T extends Comparable<? super T>>
        T[] shellSort(T[] array)
    {
        return shellSort(array, 0, array.length);
    }

    /* 希尔排序(自定义排序) */
    public static <T>
        T[] shellSort(T[] array, int from, int to, Comparator<? super T> comp)
    {
        T curVal; int i, j;
        for (int gap = (to - from) / 2; gap > 0; gap /= 2) { // 步长, 初始值为 len / 2
            for (i = gap + from; i < to; i++) {
                curVal = array[i];
                for (j = i - gap; j >= from && comp.compare(array[j], curVal) > 0; j -= gap)
                    array[j + gap] = array[j];
                array[j + gap] = curVal;
            }
        }
        return array;
    }
    public static <T>
        T[] shellSort(T[] array, Comparator<? super T> comp)
    {
        return shellSort(array, 0, array.length, comp);
    }

    /* 选择排序(自然排序) */
    public static <T extends Comparable<? super T>>
        T[] selectSort(T[] array, int from, int to)
    {
        T tmp;
        for (int i = from, min, j; i < to - 1; i++) { // 存储极值的位置
            min = i;
            for (j = i + 1; j < to; j++) // 遍历剩余序列, 找出最小元素所在的位置
                if (array[j].compareTo(array[min]) < 0)
                    min = j;
            if (min != i) {
                tmp = array[i];
                array[i] = array[min];
                array[min] = tmp;
            }
        }
        return array;
    }
    public static <T extends Comparable<? super T>>
        T[] selectSort(T[] array)
    {
        return selectSort(array, 0, array.length);
    }

    /* 选择排序(自定义排序) */
    public static <T>
        T[] selectSort(T[] array, int from, int to, Comparator<? super T> comp)
    {
        T tmp;
        for (int i = from, j, min; i < to - 1; i++) { // 存放极值的位置
            min = i;
            for (j = i + 1; j < to; j++)
                if (comp.compare(array[j], array[min]) < 0)
                    min = j;
            if (min != i) {
                tmp = array[min];
                array[min] = array[i];
                array[i] = tmp;
            }
        }
        return array;
    }
    public static <T>
        T[] selectSort(T[] array, Comparator<? super T> comp)
    {
        return selectSort(array, 0, array.length, comp);
    }

    /* 堆排序(自然排序) */
    // 最大堆(max-heap)构造
    static <T extends Comparable<? super T>>
        void maxHeapBuild(T[] array, int index, int from, int to)
    {
        int parent = index;
        int child = 2 * parent + 1 - from; // left-child
        T tmp;

        while (child < to) {
            if (child + 1 < to && array[child + 1].compareTo(array[child]) > 0)
                child++; // right-child

            if (array[parent].compareTo(array[child]) >= 0) {
                return;
            } else {
                tmp = array[parent];
                array[parent] = array[child];
                array[child] = tmp;

                parent = child;
                child = 2 * parent + 1 - from;
            }
        }
    }
    // 最大堆(max-heap)调整
    static <T extends Comparable<? super T>>
        void maxHeapAdjust(T[] array, int from, int to)
    {
        int parent = from;
        int child = 2 * parent + 1 - from; // left-child
        T tmp;

        while (child < to) {
            if (child + 1 < to && array[child + 1].compareTo(array[child]) > 0)
                child++; // right-child

            if (array[parent].compareTo(array[child]) >= 0) {
                return;
            } else {
                tmp = array[parent];
                array[parent] = array[child];
                array[child] = tmp;

                parent = child;
                child = 2 * parent + 1 - from;
            }
        }
    }
    // 最大堆排序
    public static <T extends Comparable<? super T>>
        T[] heapSort(T[] array, int from, int to)
    {
        // 构造最大堆(从最后一个非叶结点开始, 从右往左, 从下到上, 依次进行堆调整)
        for (int i = (to - from) / 2 - 1 + from; i >= from; i--)
            maxHeapBuild(array, i, from, to);

        // 将堆顶元素与尾部元素交换, 接着进行堆调整, 重复此步骤, 直到只剩一个元素为止
        T tmp;
        for (int i = to - 1; i > from; i--) {
            // swap
            tmp = array[i];
            array[i] = array[from];
            array[from] = tmp;
            // 堆调整
            maxHeapAdjust(array, from, i);
        }

        return array;
    }
    public static <T extends Comparable<? super T>>
        T[] heapSort(T[] array)
    {
        return heapSort(array, 0, array.length);
    }

    /* 堆排序(自定义排序) */
    static <T>
        void maxHeapBuild(T[] array, int index, int from, int to, Comparator<? super T> comp)
    {
        int parent = index;
        int child = 2 * parent + 1 - from; // left-child

        T tmp;
        while (child < to) {
            if (child + 1 < to && comp.compare(array[child + 1], array[child]) > 0)
                child++; // right-child

            if (comp.compare(array[parent], array[child]) >= 0) {
                return;
            } else {
                tmp = array[parent];
                array[parent] = array[child];
                array[child] = tmp;

                parent = child;
                child = 2 * parent + 1 - from;
            }
        }
    }
    static <T>
        void maxHeapAdjust(T[] array, int from, int to, Comparator<? super T> comp)
    {
        int parent = from;
        int child = 2 * parent + 1 - from; // left-child

        T tmp;
        while (child < to) {
            if (child + 1 < to && comp.compare(array[child + 1], array[child]) > 0)
                child++; // right-child

            if (comp.compare(array[parent], array[child]) >= 0) {
                return;
            } else {
                tmp = array[parent];
                array[parent] = array[child];
                array[child] = tmp;

                parent = child;
                child = 2 * parent + 1 - from;
            }
        }
    }
    public static <T>
        T[] heapSort(T[] array, int from, int to, Comparator<? super T> comp)
    {
        // 构造最大堆
        for (int i = (to - from) / 2 - 1 + from; i >= from; i--)
            maxHeapBuild(array, i, from, to, comp);

        // 取出最大元素, 然后调整最大堆
        T tmp;
        for (int i = to - 1; i > from; i--) {
            tmp = array[i];
            array[i] = array[from];
            array[from] = tmp;

            maxHeapAdjust(array, from, i, comp);
        }

        return array;
    }
    public static <T>
        T[] heapSort(T[] array, Comparator<? super T> comp)
    {
        return heapSort(array, 0, array.length, comp);
    }

    /* 冒泡排序(自然排序) */
    public static <T extends Comparable<? super T>>
        T[] bubbleSort(T[] array, int from, int to)
    {
        boolean sorted; T tmp;
        for (int i = 0, j; i < to - from - 1; i++) { // 进行 len - 1 轮比较
            sorted = true; // 假设已排好序
            for (j = from; j < to - 1 - i; j++) { // 进行本轮比较
                if (array[j].compareTo(array[j + 1]) > 0) {
                    sorted = false;
                    tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                }
            }
            if (sorted)
                break;
        }
        return array;
    }
    public static <T extends Comparable<? super T>>
        T[] bubbleSort(T[] array)
    {
        return bubbleSort(array, 0, array.length);
    }

    /* 冒泡排序(自定义排序) */
    public static <T>
        T[] bubbleSort(T[] array, int from, int to, Comparator<? super T> comp)
    {
        boolean sorted; T tmp;
        for (int i = 0, j; i < to - from - 1; i++) { // 最多进行 len - 1 轮循环
            sorted = true; // 先假设已排序
            for (j = from; j < to - 1 - i; j++) {
                if (comp.compare(array[j], array[j + 1]) > 0) {
                    sorted = false;
                    tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                }
            }
            if (sorted) // 如果假设成立, 则排序完成
                break;
        }
        return array;
    }
    public static <T>
        T[] bubbleSort(T[] array, Comparator<? super T> comp)
    {
        return bubbleSort(array, 0, array.length, comp);
    }

    /* 快速排序(自然排序) */
    // 原地分区(in-place)
    static <T extends Comparable<? super T>>
        int partition(T[] array, int left, int right)
    {
        // 选择 mid 元素为 pivot 基准元素
        int pivotInd = (left + right) / 2;
        T pivotVal = array[pivotInd];

        // 交换 pivotVal 和最后一个元素
        array[pivotInd] = array[right];
        array[right] = pivotVal;

        int storeInd = left; T tmp;
        for (int i = left; i < right; i++) {
            if (array[i].compareTo(pivotVal) <= 0) {
                if (i != storeInd) {
                    tmp = array[i];
                    array[i] = array[storeInd];
                    array[storeInd] = tmp;
                }
                storeInd++;
            }
        }
        array[right] = array[storeInd];
        array[storeInd] = pivotVal;

        return storeInd;
    }
    // 递归分区(recursive partition)
    static <T extends Comparable<? super T>>
        void qSort(T[] array, int left, int right)
    {
        if (left < right) {
            int storeInd = partition(array, left, right);
            qSort(array, left, storeInd - 1);
            qSort(array, storeInd + 1, right);
        }
    }
    // 快速排序(quick sort)
    public static <T extends Comparable<? super T>>
        T[] quickSort(T[] array, int from, int to)
    {
        qSort(array, from, to - 1);
        return array;
    }
    public static <T extends Comparable<? super T>>
        T[] quickSort(T[] array)
    {
        return quickSort(array, 0, array.length);
    }

    /* 快速排序(自定义排序) */
    static <T>
        int partition(T[] array, int left, int right, Comparator<? super T> comp)
    {
        // 中间位置作为 pivot
        int pivotInd = (left + right) / 2;
        T pivotVal = array[pivotInd];

        // 交换 array[pivotInd] 与 array[right]
        array[pivotInd] = array[right];
        array[right] = pivotVal;

        // 将"小于" pivotVal 的元素交换至序列"开头"
        int storeInd = left; T tmp;
        for (int i = left; i < right; i++) {
            if (comp.compare(array[i], pivotVal) <= 0) {
                if (storeInd != i) {
                    tmp = array[storeInd];
                    array[storeInd] = array[i];
                    array[i] = tmp;
                }
                storeInd++;
            }
        }
        // 将 pivotVal 放回正确(已排序)的位置
        array[right] = array[storeInd];
        array[storeInd] = pivotVal;

        return storeInd;
    }
    static <T>
        void qSort(T[] array, int left, int right, Comparator<? super T> comp)
    {
        if (left < right) { // 至少两个元素
            int storeInd = partition(array, left, right, comp);
            qSort(array, left, storeInd - 1, comp);
            qSort(array, storeInd + 1, right, comp);
        }
    }
    public static <T>
        T[] quickSort(T[] array, int from, int to, Comparator<? super T> comp)
    {
        qSort(array, from, to - 1, comp);
        return array;
    }
    public static <T>
        T[] quickSort(T[] array, Comparator<? super T> comp)
    {
        return quickSort(array, 0, array.length, comp);
    }

    /* 归并排序(自然排序) */
    static <T extends Comparable<? super T>>
        void mSort(T[] arr, int beg, int end, T[] tmp)
    {
        if (beg < end) { // 至少两个元素才进行分割
            // 递归分解
            int mid = (beg + end) / 2;
            mSort(arr, beg, mid, tmp);
            mSort(arr, mid + 1, end, tmp);

            // 合并两个有序的子序列
            int i = beg,        // 左子序列
                j = mid + 1,    // 右子序列
                k = 0;          // 临时序列
            // 比较左右子序列, 依次放入
            while (i <= mid && j <= end) {
                if (arr[i].compareTo(arr[j]) <= 0)
                    tmp[k++] = arr[i++];
                else
                    tmp[k++] = arr[j++];
            }
            // 剩余的序列元素直接放进去
            while (i <= mid)
                tmp[k++] = arr[i++];
            while (j <= end)
                tmp[k++] = arr[j++];
            // 最后将 tmp 数组的内容放回 arr
            for (int m = 0; m < k; m++)
                arr[beg + m] = tmp[m];
        }
    }
    public static <T extends Comparable<? super T>>
        T[] mergeSort(T[] array, int from, int to)
    {
        @SuppressWarnings("unchecked")
        T[] tmp = (T[]) Array.newInstance(array[from].getClass(), to - from);
        mSort(array, from, to - 1, tmp);
        return array;
    }
    public static <T extends Comparable<? super T>>
        T[] mergeSort(T[] array)
    {
        return mergeSort(array, 0, array.length);
    }

    /* 归并排序(自定义排序) */
    static <T>
        void mSort(T[] arr, int beg, int end, T[] tmp, Comparator<? super T> comp)
    {
        if (beg < end) { // 至少两个元素
            int mid = (beg + end) / 2;
            mSort(arr, beg, mid, tmp, comp);
            mSort(arr, mid + 1, end, tmp, comp);

            int i = beg,     // left
                j = mid + 1, // right
                k = 0;       // temp

            while (i <= mid && j <= end) {
                if (comp.compare(arr[i], arr[j]) <= 0)
                    tmp[k++] = arr[i++];
                else
                    tmp[k++] = arr[j++];
            }

            while (i <= mid)
                tmp[k++] = arr[i++];
            while (j <= end)
                tmp[k++] = arr[j++];

            for (int m = 0; m < k; m++)
                arr[beg + m] = tmp[m];
        }
    }
    public static <T>
        T[] mergeSort(T[] array, int from, int to, Comparator<? super T> comp)
    {
        @SuppressWarnings("unchecked")
        T[] tmp = (T[]) Array.newInstance(array[from].getClass(), to - from);
        mSort(array, from, to - 1, tmp, comp);
        return array;
    }
    public static <T>
        T[] mergeSort(T[] array, Comparator<? super T> comp)
    {
        return mergeSort(array, 0, array.length, comp);
    }

    /* 翻转数组 */
    public static <T>
        T[] reverse(T[] array, int from, int to)
    {
        int mid = (from + to) / 2; T tmp;
        for (int i = from, off = 0; i < mid; i++, off++) {
            tmp = array[i];
            array[i] = array[to - 1 - off];
            array[to - 1 - off] = tmp;
        }
        return array;
    }
    public static <T>
        T[] reverse(T[] array)
    {
        return reverse(array, 0, array.length);
    }

    /* 随机洗牌 */
    public static <T>
        T[] shuffle(T[] array, int from, int to)
    {
        Random rnd = new Random(); T tmp;
        for (int i = to - 1, j; i > from; i--) {
            j = rnd.nextInt(i + 1 - from) + from;
            if (j != i) {
                tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
            }
        }
        return array;
    }
    public static <T>
        T[] shuffle(T[] array)
    {
        return shuffle(array, 0, array.length);
    }
}
