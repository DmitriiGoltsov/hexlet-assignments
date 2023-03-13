package exercise;

// BEGIN
class ReversedSequence implements CharSequence {

    private final String text;

    ReversedSequence(String sequence) {
        StringBuilder result = new StringBuilder();

        String[] items = sequence.split("");

        for (int i = items.length - 1; i >= 0; i--) {
            result.append(items[i]);
        }

        this.text = result.toString();
    }

    @Override
    public int length() {
        return text.length();
    }

    @Override
    public char charAt(int i) {
        return this.text.charAt(i);
    }

    @Override
    public CharSequence subSequence(int i, int i1) {

        String[] items = this.text.split("");
        StringBuilder result = new StringBuilder();

        for (int index = i; index < i1; i++) {
            result.append(items[i]);
        }

        return result.toString();
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
// END
