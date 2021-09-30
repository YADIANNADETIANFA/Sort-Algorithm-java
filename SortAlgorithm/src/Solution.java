import java.util.ArrayList;
import java.util.Random;

public class Solution {
    private static void mySwap(ArrayList<Integer> nums, int i, int j){
        //用get,set方法遍历和改变ArrayList中的值
        int temp = nums.get(i);
        nums.set(i,nums.get(j));
        nums.set(j,temp);
    }

    public static ArrayList<Integer> bubbleSort(ArrayList<Integer> nums){
        int n = nums.size();
        if(n <= 1){
            return nums;
        }
        for(int i=0;i<n-1;++i){
            for(int j=0;j<n-1-i;++j){
                if(nums.get(j)>nums.get(j+1)){
                    mySwap(nums,j,j+1);
                }
            }
        }
        return nums;
    }

    public static ArrayList<Integer> selectSort(ArrayList<Integer> nums){
        int n = nums.size();
        if(n<=1){
            return nums;
        }
        for(int i=0;i<n;++i){
            int min=i; //min是当前最小值的下标
            for(int j=i+1;j<n;++j){
                if(nums.get(j)<nums.get(min)){
                    min = j;
                }
            }
            if(min != i){
                mySwap(nums,i,min);
            }
        }
        return nums;
    }

    public static ArrayList<Integer> insertSort(ArrayList<Integer> nums){
        int n = nums.size();
        if(n<=1){
            return nums;
        }
        for(int i=1;i<n;++i){
            int get = nums.get(i);
            int j = i-1;
            while(j>=0 && nums.get(j)>get){
                nums.set(j+1,nums.get(j));//后移一位
                --j;
            }
            nums.set(j+1,get);
        }
        return nums;
    }

    public static ArrayList<Integer> shellSort(ArrayList<Integer> nums){
        int n = nums.size();
        if(n<=1){
            return nums;
        }
        int h = 0;
        while(h<=n){
            h = h*3+1;
        }

        while(h>=1){
            //插入排序的首要原则是保证该元素的前面已经是排好序的了
            //不同分组同时处理，即好几个间隔都是h的分组，同时进行插入排序
            for(int i=h;i<n;++i){
                int get = nums.get(i);
                int j = i-h;
                while(j>=0 && nums.get(j)>get){
                    nums.set(j+h,nums.get(j));//后移h位
                    j -= h;
                }
                nums.set(j+h,get);
            }
            h = (h-1)/3;
        }
        return nums;
    }

    //合并两个已经排好序的数组nums[left,...,mid]和nums[mid+1,...,right]
    private static ArrayList<Integer> merge(ArrayList<Integer> nums, int left, int mid, int right){
        ArrayList<Integer> temp = new ArrayList<>(right - left + 1); //指定长度,辅助空间
        int i = left;   //前一子数组的起始元素
        int j = mid + 1;    //右子数组的起始元素
        while(i<=mid && j<=right){
            int val = nums.get(i)<=nums.get(j) ? nums.get(i++) : nums.get(j++); //记得i j的 ++
            temp.add(val);
        }
        while(i<=mid){
            temp.add(nums.get(i++));    //勿忘++
        }
        while(j<=right){
            temp.add(nums.get(j++));    //勿忘++
        }
        for(var num:temp){
            nums.set(left++,num);
        }
        return nums;
    }

    public static ArrayList<Integer> mergeSortRecursion(ArrayList<Integer> nums, int left, int right){   //递归，自顶向下
        if(left == right){
            return nums;
        }
        int mid = (left + right)/2;
        nums = mergeSortRecursion(nums,left,mid);
        nums = mergeSortRecursion(nums,mid+1,right);
        nums = merge(nums,left,mid,right);
        return nums;
    }

    public static ArrayList<Integer> mergeSortIteration(ArrayList<Integer> nums){  //迭代，自底向上
        int len = nums.size();
        if(len<=1){
            return nums;
        }
        int left = 0;
        int mid = 0;
        int right = 0;
        for(int i=1;i<len;i*=2){    //相邻两个子数组，前一个子数组的大小为i；每轮翻倍
            left = 0;
            while(left+i<len){  //后一子数组存在，需要归并； left+i是后一子数组的起始下标
                mid = left+i-1;
                right = (mid + i)<(len-1) ? (mid + i) : (len - 1);  //后一子数组大小可能越界
                merge(nums,left,mid,right);
                left = right + 1;   //归并每一轮后面的内容
            }
        }
        return nums;
    }

    private static int partition(ArrayList<Integer> nums, int start, int end){
        int index = randomInRange(start,end);
        mySwap(nums,index,end);
        int small = start - 1;
        for(index=start;index<end;++index){
            if(nums.get(index)<nums.get(end)){
                ++small;
                if(index != small){
                    mySwap(nums,index,small);
                }
            }
        }
        ++small;
        mySwap(nums,small,end);
        return small;
    }
    private static int randomInRange(int start,int end){
        if(start<end){
            Random random = new Random();   //不置种子，以当前时间进行随机
            return random.nextInt(end-start+1)+start;   //return random.nextInt(10)+5;获取[5,15)范围内的随机整数
        }
        return start;
    }

    //调用前，请先对nums以及start，end进行一下判断,避免在递归的过程中过度重复冗余判断
    public static ArrayList<Integer> quickSort(ArrayList<Integer> nums, int start, int end){
        if(start == end){
            return nums;
        }
        int index = partition(nums,start,end);
        if(index > start){
            quickSort(nums,start,index-1);
        }
        if(index < end){
            quickSort(nums,index+1,end);
        }
        return nums;
    }

    private static void heapify(ArrayList<Integer> nums, int parent, int heap_size){
        int left = 2*parent+1;
        int right = 2*parent+2;
        int max = parent;
        if(left<heap_size && nums.get(left)>nums.get(max)){
            max = left;
        }
        if(right<heap_size && nums.get(right)>nums.get(max)){
            max = right;
        }
        if(max != parent){
            mySwap(nums,parent,max);
            heapify(nums,max,heap_size);
        }

    }

    private static void buildHeap(ArrayList<Integer> nums,int heap_size){   //heap_size为当前堆内的元素个数
        for(int i=(heap_size-1)/2;i>=0;--i){    //i为选定的父节点
            heapify(nums,i,heap_size);  //从上往下堆排序，只能保证当前最大值点在上面，保证不了绝对最大值点在上面。即容易导致上面的点有可能要小于下面的点
        }
    }

    public static ArrayList<Integer> heapSort(ArrayList<Integer> nums){
        if(null == nums || nums.size()<=1){
            return nums;
        }
        int heap_size = nums.size();
        buildHeap(nums,heap_size);
        while(heap_size>1){
            --heap_size;
            mySwap(nums,0,heap_size);
            heapify(nums,0,heap_size);
        }
        return nums;
    }

    private static int getIndex(ArrayList<Integer> nums){
        int max = nums.get(0);
        for(var i:nums){
            if(i>max){
                max=i;
            }
        }
        int index=1;
        int radix=10;
        while(max>=radix){
            ++index;
            radix *= 10;
        }
        return index;
    }

    //基数排序（桶排序的一种）
    public static ArrayList<Integer> radixSort(ArrayList<Integer> nums){
        if(null == nums || nums.size()<=1){
            return nums;
        }
        int index = getIndex(nums);

        ArrayList<ArrayList<Integer>> bucket = new ArrayList<>();
        //bucket的创建，注意
        for(int i=0;i<10;++i){
            bucket.add(new ArrayList<>());
        }

        for(int i=1;i<=index;++i){
            //bucket.clear(); //直接bucket.clear();将导致bucket.get((int)bucketnum).add(num);空指针异常
            //所以要逐个clear才可以
            for(int k=0;k<10;++k){
                bucket.get(k).clear();
            }
            for(var num:nums){
                double bucketnum = num % Math.pow(10,i) / Math.pow(10,i-1);
                bucket.get((int)bucketnum).add(num);
            }
            int q=0;
            for(var temp:bucket){
                for(var tempIn:temp){
                    nums.set(q++,tempIn);
                }
            }
        }
        return nums;
    }
    /*
    * （1）首先，按个位排序入桶，个位小的数在前面，即49不会在44,45的前面
    * （2）然后，再按十位排序入桶，虽然十位相同，但也只会44,45被先扫描入库，49被后扫描入库
    * （3）若有百位，再按百位排序...
    * 以此类推，最终拿到排序好的数组
    * */
}
