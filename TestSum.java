public class TestSum {

	public static int[] arrAdd(int[] arr, int v) {
		int[] newArr = new int[arr.length + 1];
		for (int i = 0; i < arr.length; ++i) {
			newArr[i] = arr[i];
		}
		newArr[arr.length] = v;
		return newArr;
	}

	public static int[] arrSub(int[] arr, int skipIndex) {
		int[] newArr = new int[arr.length - 1];
		int j = 0;
		for(int i = 0; i < arr.length; ++i) {
			if (i != skipIndex) {
				newArr[j] = arr[i];
				++j;
			}
		}
		return newArr;
	}

	static void print(int[] arr) {
		String s = "";
		for (int i : arr) {
			s += " " + i;
		}
		System.out.println(s);
	}

	public static void knapsack(int restNum, int[] chosen, int[] rest) {
		if (restNum == 0) {
			print(chosen);
		} else {
			for (int i = 0; i < rest.length; i++) {
				int c = rest[i];
				if (c <= restNum) {
					knapsack(restNum - c, arrAdd(chosen, c), arrSub(rest, i));
				}
			}
		}
	}

	public static void main(String[] args) {
		int[] arr = new int[] {1, 2, 3, 4, 5, 6, 7};
		int target = 17;
		knapsack(target, new int[0], arr);
	}
}
