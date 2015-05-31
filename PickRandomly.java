import java.util.*;

public class PickRandomly {

	int reservoir = -1;

	int pick(Iterable<Integer> iter) {
		int index = 1;
		Random random = new Random();
		for(Integer v : iter) {
			int rand = random.nextInt(index) + 1;
			if (rand == 1) {
				reservoir = v;
			}
			index++;
		}
		return reservoir;
	}

	public static void main(String[] args) {
		PickRandomly picker = new PickRandomly();
		List<Integer> list = new ArrayList();
		for (int i : new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9}) {
			list.add(i);
		}
		int[] arr = new int[10];
		for (int i = 0; i < 10_000; ++i) {
			++arr[picker.pick(list)];
		}
		for (int i: arr) {
			System.out.println(i);
		}
	}
}
