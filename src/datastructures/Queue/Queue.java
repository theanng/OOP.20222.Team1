package structure.Queue;

import structure.List.List;

public class Queue extends List{


    public Queue(int size) {
        super(size);
    }

    public void enqueue(int item) {
        if (this.isFull()) {
            System.out.println("Full");
            return;
        }
        this.add(item);
    }

    public int dequeue() {
        if (isEmpty()) {
            System.out.println("Hàng đợi trống. Không thể xóa phần tử.");
            return -1;
        }
        int removedItem = this.get(0);
        this.remove(0);
        return removedItem;
    }

    public int peek() {
        if (isEmpty()) {
            System.out.println("Hàng đợi trống. Không có phần tử để xem.");
            return -1;
        }
        return this.get(0);
    }

    public int peekBack() {
        if (isEmpty()) {
            System.out.println("Hàng đợi trống. Không có phần tử để xem.");
            return -1;
        }
        return this.get(this.size()-1);
    }

}
