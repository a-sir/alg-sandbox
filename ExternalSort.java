import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.*;
import java.util.stream.*;

public class ExternalSort {

	private static List<File> sortedChunks(File dir) {
		File[] childs = dir.listFiles(f -> f.getName().startsWith("sorted_") && f.getName().endsWith(".part"));
		if (childs == null) {
			return Collections.emptyList();
		} else {
			return Arrays.asList(childs);
		}
	}

	public static void main(String[] args) throws Exception {
		File input = new File(args[0]);
		File dir = input.getParentFile();
		sortedChunks(dir).stream().forEach(File::delete);

		final int BUF_LIMIT = 1000_000;
		List<String> buffer = new ArrayList<>();
		int index = 0;
		int size = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(input))) {
			String line;
			while((line = br.readLine()) != null) {
				buffer.add(line);
				size += line.getBytes().length;
				if (size >= BUF_LIMIT) {
					Path path = new File(dir, "sorted_" + index + ".part").toPath();
					buffer.sort(null);
					Files.write(path, buffer, Charset.defaultCharset());
					buffer.clear();
					size = 0;
					index++;
				}
			}
			if (size >= BUF_LIMIT) {
				Path path = new File(dir, "sorted_" + index + ".part").toPath();
				buffer.sort(null);
				Files.write(path, buffer, Charset.defaultCharset());
				buffer.clear();
				size = 0;
				index++;
			}
			System.out.println("Chunks are ready.");
		}

		merge(sortedChunks(dir), new File(dir, input.getName() + "sorted"));
	}

	private static void merge(List<File> chunk, File result) throws IOException {
		List<Queue> queues = chunk.stream().map(Queue::new).filter(q -> !q.isEmpty()).collect(Collectors.toList());
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(result))) {
			while (!queues.isEmpty()) {
				queues.sort(null);
				Queue min = queues.get(0);
				bw.write(min.poll());
				bw.newLine();
				if (min.isEmpty()) {
					queues.remove(min);
				}
			}
		}
		System.out.println("Done.");
	}

	private static class Queue implements Comparable<Queue> {
		private BufferedReader br;
		private String current;

		public Queue(File f) {
			try {
				br = new BufferedReader(new FileReader(f));
				current = br.readLine();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		public String peek() {
			return current;
		}

		public boolean isEmpty() {
			return current == null;
		}

		public String poll() throws IOException {
			String res = current;
			if (current != null) {
				current = br.readLine();
			}
			return res;
		}

		public void close() throws IOException {
			br.close();
		}

		public int compareTo(Queue other) {
			return this.peek().compareTo(other.peek());
		}

	}
}
