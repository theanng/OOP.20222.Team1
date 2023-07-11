package datastructures.list;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class List {
    private int[] elements;
    private static int maxSize=10;
    private int size;

    public List(int size) {
        maxSize = size;
        elements = new int[maxSize]; // Initial capacity
        size = 0;
    }
    
    public void add(int element) {
        if (size == elements.length) {
            System.out.println("full");
        }
        
        elements[size++] = element;
    }
    
    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        
        return elements[index];
    }
    
    public int size() {
        return size;
    }
    
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        size--;
    }
    
    public boolean contains(int element) {
        for (int i = 0; i < size; i++) {
            if (elements[i]==element) {
                return true;
            }
        }
        
        return false;
    }
    
    public void clear() {
        Arrays.fill(elements, 0);
        size = 0;
    }

    public int find(int t) {
        boolean a = false;
        int b =0;
        for (int i = 0; i < size; i++) {
            if (elements[i]==t) {
                a=true;
                b=i;
                break;
            }
        }
        if (a==false) {
            throw new NoSuchElementException();
        }
        else {
            return b;
        }
    }

    public void print() {
        for (int i=0; i<this.size();i++) {
            System.out.println(this.get(i));
        }
    }

    public boolean isFull() {
        return size == maxSize;
    }

    public boolean isEmpty() {
        return size == 0;
    }
    public int[] toArray() {
        int[] array = new int[size];
        for (int i = 0; i <= size-1; i++) {
            array[i] = elements[i];
        }
        return array;
    }
    //bubble sort
    public void sort() {
        for (int i=0;i<size-1;++i){
            for(int j=0;j<size-i-1; ++j){
                if(elements[j+1]<elements[j]){
                    int swap = elements[j];
                    elements[j] = elements[j+1];
                    elements[j+1] = swap;
                }
            }
        }
    }

    public void insertAtIndex(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (size == elements.length) {
            System.out.println("full");
        }

        // Di chuyển các phần tử từ vị trí index sang phải để tạo chỗ trống cho phần tử mới
        System.arraycopy(elements, index, elements, index + 1, size - index);

        elements[index] = value;
        size++;
    }
}
