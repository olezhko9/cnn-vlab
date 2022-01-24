package vlab.server_java;

import org.json.JSONArray;

import java.util.Arrays;

public class Matrix {
    public static void main(String[] args) {
        int[][] image = {
                {2, 4, 9, 1, 4},
                {2, 1, 4, 4, 6},
                {1, 1, 2, 9, 2},
                {7, 3, 5, 1, 3},
                {2, 3, 4, 8, 5}
        };
        int[][] filter = {
                {1, 2, 3},
                {-4, 7, 4},
                {2, -5, 1}
        };

        Matrix imageM = new Matrix(image);
        Matrix kernelM = new Matrix(filter);
        Matrix feature = imageM.convolve(kernelM, 1, 1);
        feature.print();
        System.out.println("-----------------");
        Matrix maxPooledFeature = feature.pool(2);
        maxPooledFeature.print();
    }

    public final int[][] data;
    public final int size;

    private Matrix(int[][] data) {
        this.data = data;
        this.size = data.length;
    }

    public static Matrix fromJSON(JSONArray jsonMatrix) {
        int N = jsonMatrix.length();
        int[][] data = new int[N][];
        for (int i = 0; i < N; i++) {
            JSONArray jsonArray = jsonMatrix.optJSONArray(i);
            int[] intArray = new int[N];
            for (int j = 0; j < N; j++) {
                intArray[j] = jsonArray.optInt(j, -1000);
            }
            data[i] = intArray;
        }
        return new Matrix(data);
    }

    public Matrix pad(int padding, int v) {
        int newSize = this.size + padding * 2;
        int[][] temp = new int[newSize][newSize];
        for (int i = 0; i < temp.length; i++) {
            Arrays.fill(temp[i], v);
        }
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                temp[i + padding][j + padding] = this.data[i][j];
            }
        }

        return new Matrix(temp);
    }

    public Matrix subset(int row, int col, int size) {
        int[][] temp = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                temp[i][j] = this.data[row + i][col + j];
            }
        }

        return new Matrix(temp);
    }

    public Matrix multiply(Matrix matrix) {
        int[][] temp = new int[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                temp[i][j] = this.data[i][j] * matrix.data[i][j];
            }
        }

        return new Matrix(temp);
    }

    public int sum() {
        int sum = 0;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                sum += this.data[i][j];
            }
        }

        return sum;
    }

    public int max() {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.data[i][j] > max) max = this.data[i][j];
            }
        }

        return max;
    }

    public Matrix convolve(Matrix kernel, int padding, int stride) {
        Matrix padMatrix = this.pad(padding, 0);

        int newSize = (padMatrix.size - kernel.size) / stride + 1;
        int[][] temp = new int[newSize][newSize];

        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                Matrix subMatrix = padMatrix.subset(stride * i, stride * j, kernel.size);
                temp[i][j] = subMatrix.multiply(kernel).sum();;
            }
        }

        return new Matrix(temp);
    }

    public Matrix pool(int filterSize) {
        final int stride = 2;

        int newSize = (this.size - filterSize) / stride + 1;
        int[][] temp = new int[newSize][newSize];

        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                Matrix subMatrix = this.subset(stride * i, stride * j, filterSize);
                temp[i][j] = subMatrix.max();
            }
        }

        return new Matrix(temp);
    }

    /**
     * @return число несовпадающих элементов
     */
    public int compare(Matrix matrix) {
        int notEqual = 0;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.data[i][j] != matrix.data[i][j]) {
                    notEqual += 1;
                }
            }
        }

        return notEqual;
    }

    public static Matrix getRandomMatrix(int size, int minValue, int maxValue) {
        int[][] temp = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                temp[i][j] = Utils.getRandomIntInRange(minValue, maxValue);
            }
        }

        return new Matrix(temp);
    }

    public void print() {
        for (int i = 0; i < this.data.length; i++) {
            for (int j = 0; j < this.data[i].length; j++) {
                System.out.print(data[i][j] + "\t");
            }
            System.out.println("\n");
        }
    }
}
