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

	private void replaceRoot(int index) {
		// 1 node to replace
		int leftIndex = arr[1];
		int rightIndex = arr[2];
	}

	private boolean isLeaf(int index) {
		boolean res =
			index != -1 && arr[index] != -1 &&
			arr[index + 1] == -1 && arr[index + 2] == -1;
		System.out.println("isLeaf index: " + index + " res: " + res);
		return res;
	}

	private int takeLeaf(int index) {
		System.out.println("takeLeaf: " + index);
		if (isLeaf(arr[index + 1])) {
			int res = arr[index + 1];
			arr[index + 1] = -1;
			return res;
		} else if (isLeaf(arr[index + 2])) {
			int res = arr[index + 2];
			arr[index + 2] = -1;
			return res;
		} else if (arr[index + 1] != -1) {
			return takeLeaf(arr[index + 1]);
		} else if (arr[index + 2] != -1) {
			return takeLeaf(arr[index + 2]);
		} else {
			throw new RuntimeException("take leaf called in leaf " + index);
		}
	}

	private void swapMinUp(int iRoot, int iChild) {
		if (arr[iRoot] > arr[iChild]) {
			int t = arr[iChild];
			arr[iChild] = arr[iRoot];
			arr[iRoot] = t;
			sink(iChild);
		}
	}

	private void sink(int idx) {
		if (arr[idx + 1] == -1 && arr[idx + 2] == -1) {
			return;
		}

		if(arr[idx + 2] == -1) {
			swapMinUp(idx, arr[idx + 1]);
		} else if(arr[idx + 1] == -1) {
			swapMinUp(idx, arr[idx + 2]);
		} else {
			int min = Math.min(arr[idx + 1], arr[idx + 2]);
			swapMinUp(idx, min);
		}
	}

	private void changeLink(int from, int to) {
		for (int k = 0; k < nextEmpty; k += 3) {
			if (arr[k + 1] == from) {
				arr[k + 1] = to;
			}
			if (arr[k + 2] == from) {
				arr[k + 2] = to;
			}
		}
	}

	private int getLastNotEmpty(int offsetExcluding) {
		for (int i = nextEmpty - 3 ; i >= offsetExcluding ; i -= 3) {
			if (arr[i] != -1) {
				System.out.println("Last not empty " + i);
				return i;
			}
		}
		return -1;
	}

	private void compact() {
		System.out.println("Before compact: " + this);
		int compacted = 0;
		for (int i = 0 ; i < nextEmpty ; i += 3) {
			if (arr[i] == -1) {
				System.out.println("Found empty element " + i);
				if (i == nextEmpty - 3) {
					nextEmpty -= 3;
				} else {
					int j = getLastNotEmpty(i);
					if (j != -1) {
						copy(j, i);
						changeLink(j, i);
						compacted++;
					} else {
						throw new RuntimeException("Empty tail " + this);
					}
				}
			}
		}
		nextEmpty -= compacted * 3;
		System.out.println("Compacted: " + this);
	}

	public int poll() {
		int min = arr[0];
		if (min == -1) {
			return -1;
		}

		System.out.println("Poll choosed min:" + min + ", take leaf to fill root.");
		// choose any leaf as root
		if (isLeaf(0)) {
			System.out.println("Root is leaf already.");
			arr[0] = -1;
			System.out.println("Current state: " + toString());
		} else {
			int leaf = takeLeaf(0);
			System.out.println("Poll chosed leaf for root: " + arr[leaf]);
			// put on first place and sink
			arr[0] = arr[leaf];
			arr[leaf] = -1;
			System.out.println("Current state: " + toString());
			sink(0);
			System.out.println("Current state after sink(0): " + toString());
		}

		compact();

		return min;
	}

	public static void main(String[] args) {
		HeapSort hs = new HeapSort();
		hs.add(2);
		System.out.println(hs);

		hs.add(1);
		hs.add(3);

		System.out.println(hs);
		int min;
		while ((min = hs.poll()) != -1) {
			System.out.println("Polled " + min);
		}
	}
}
