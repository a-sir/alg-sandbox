class HeapSort {

	int[] arr;
	int nextEmpty = 0;

	public HeapSort() {
		arr = new int[300];
		arr[0] = -1;
	}

	private int getNew() {
		int i = nextEmpty;
		nextEmpty += 3;
		if (arr.length < nextEmpty + 3) {
			int[] newArr = new int[arr.length * 2];
			System.arraycopy(arr, 0, newArr, 0, arr.length);
			arr = newArr;
		}
		System.out.println("getNew " + i);
		return i;
	}

	public void add(int val) {
		add(0, val);
	}

	private void swap(int index, int value) {
		System.out.println("swap " + index + ", " + value);
		int i = getNew();
		copy(index, i);
		put(index, value);
	}

	private void copy(int from, int to) {
		System.out.println("copy " + from + ", " + to);
		arr[to] = arr[from];
		arr[to + 1] = arr[from + 1];
		arr[to + 2] = arr[from + 2];
	}

	private void swapNew(int index, int value) {
		System.out.println("swapNew " + index + ", " + value);
		int i = getNew();
		copy(index, i);
		arr[index] = value;
		int odd = value % 2;
		arr[index + 1] = i;
		arr[index + 2] = -1;
	}

	private void put(int index, int value) {
		System.out.println("put " + index + ", " + value);
		arr[index] = value;
		arr[index + 1] = -1;
		arr[index + 2] = -1;
	}

	public void add(int index, int val) {
		System.out.println("add " + index + ", " + val);
		if (arr[index] == -1 || index == nextEmpty) {
			put(index, val);
			nextEmpty += 3;
		} else if (val <= arr[index]) {
			swapNew(index, val);
		} else if (arr[index + 1] == -1) {
			int i = getNew();
			put(i, val);
			arr[index + 1] = i;
		} else if (arr[index + 2] == -1) {
			int i = getNew();
			put(i, val);
			arr[index + 2] = i;
		} else {
			boolean addLeft = (val % 2) == 0;
			add(arr[index + (addLeft ? 1 : 2)], val);
		}
	}

	public String toString() {
		String out = "";
		for (int i = 0 ; i < nextEmpty; ++i) {
			out += " " + arr[i];
		}
		return out;
	}

	public static void main(String[] args) {
		HeapSort hs = new HeapSort();
		hs.add(2);
		System.out.println(hs);

		hs.add(1);
		System.out.println(hs);
	}
}
