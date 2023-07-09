package structure.Stack;

import structure.List.List;

public class Stack extends List{
    public Stack(int size) {
        super(size);
    }

    public void push(int value) {
        if (this.isFull()) {
            System.out.println("Stack is full. Cannot push " + value);
        } else {
            this.add(value);
            System.out.println(value + " pushed to stack");
        }
    }

    public int pop() {
        if (this.isEmpty()) {
            System.out.println("Stack is empty. Cannot pop element.");
            return -1;
        } else {
            int value = this.get(this.size()-1);
            this.remove(this.size()-1);
            System.out.println(value + " popped from stack");
            return value;
        }
    }

    public int peek() {
        if (this.isEmpty()) {
            System.out.println("Stack is empty.");
            return -1;
        } else {
            return this.get(this.size()-1);
        }
    }
}
