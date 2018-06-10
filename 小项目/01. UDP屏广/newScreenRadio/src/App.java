import com.qidai.student.Receive;
import com.qidai.student.StudentUI;

import java.util.*;

public class App {
    public static void main(String[] args) {
        /*StudentUI studentUI = new StudentUI();
        Receive receive = new Receive(studentUI);
        receive.start();*/
       /* Integer[] ints1 = new Integer[]{6,4,2};
        int[] ints2 = new int[]{2,8,9};
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list,ints1);
        for (Integer o : list) {
            System.out.println(o);
        }*/

/*
        Integer[] ints1 = new Integer[]{6,4,2};
        Integer[] ints2 = new Integer[]{2,8,9};
        List<Integer> list = Arrays.asList(ints1);
        List<Integer> lists = new ArrayList<>(list);
        lists.forEach((e) -> System.out.println(e));
*/

        Integer[] ints1 = new Integer[]{6,4,2};
        Integer[] ints2 = new Integer[]{2,8,9};
        Set<Integer> set = new HashSet<>(Arrays.asList(ints1));
        set.forEach((e) -> System.out.println(e));


    }
}
