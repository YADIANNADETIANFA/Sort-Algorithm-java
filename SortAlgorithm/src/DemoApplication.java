import java.util.ArrayList;

public class DemoApplication {
    public static void main(String args[]){
        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(3);
        nums.add(44);
        nums.add(58);
        nums.add(10);
        nums.add(21);
        nums.add(76);
        nums.add(45);
        nums.add(49);
        nums.add(11);
        nums.add(31);

        Solution.radixSort(nums);
        for(var num:nums){
            System.out.print(num);
            System.out.print(" ");
        }
    }
}
