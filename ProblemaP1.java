//Laura Sanchez Bernal - 202411353
//Julian David Ramos Gonzalez - 202414411

import java.util.*;

public class ProblemaP1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder salida = new StringBuilder();

        int casos = sc.nextInt();

        for (int c = 0; c < casos; c++) {

            int n = sc.nextInt(); // numero de orbitas
            int m = sc.nextInt(); // numero de posiciones por orbita
            int p = sc.nextInt(); // numero de portales

            int[] e = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                e[i] = sc.nextInt(); // costo de moverse dentro de la orbita i
            }

            int[] xs = new int[p];
            int[] ys = new int[p];
            int[] xe = new int[p];
            int[] ye = new int[p];
            for (int k = 0; k < p; k++) {
                xs[k] = sc.nextInt();
                ys[k] = sc.nextInt();
                xe[k] = sc.nextInt();
                ye[k] = sc.nextInt();
            }

            long costo = calcularCostoMinimo(n, m, e, p, xs, ys, xe, ye);

            if (costo == -1) {
                salida.append("NO EXISTE\n");
            } else {
                salida.append(costo).append("\n");
            }
        }

        System.out.print(salida);
    }

    
    static long calcularCostoMinimo(int n, int m, int[] e, int p, int[] xs, int[] ys, int[] xe, int[] ye) {

        long infinito = Long.MAX_VALUE / 2;

        // D[i][j] = costo minimo para llegar desde (1,1) hasta (i,j)
        long[][] D = new long[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                D[i][j] = infinito; // al inicio, todo se considera inalcanzable
            }
        }
        D[1][1] = 0; // caso base

        for (int i = 1; i <= n; i++) {

            // barrido hacia la derecha dentro de la orbita i
            for (int j = 2; j <= m; j++) {
                long viniendoDeLaIzquierda = D[i][j - 1] + e[i];
                if (viniendoDeLaIzquierda < D[i][j]) {
                    D[i][j] = viniendoDeLaIzquierda;
                }
            }

            // barrido hacia la izquierda dentro de la orbita i
            for (int j = m - 1; j >= 1; j--) {
                long viniendoDeLaDerecha = D[i][j + 1] + e[i];
                if (viniendoDeLaDerecha < D[i][j]) {
                    D[i][j] = viniendoDeLaDerecha;
                }
            }

            // revisamos todos los portales, y usamos los que salen de la orbita i
            for (int k = 0; k < p; k++) {
                if (xs[k] == i) {
                    long costoPortal = D[xs[k]][ys[k]]; // el portal no suma costo
                    if (costoPortal < D[xe[k]][ye[k]]) {
                        D[xe[k]][ye[k]] = costoPortal;
                    }
                }
            }
        }

        if (D[n][m] >= infinito) {
            return -1; // nunca se alcanzo el destino
        }
        return D[n][m];
    }
}
