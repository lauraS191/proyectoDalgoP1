//Laura Sanchez Bernal - 202411353
//Julian David Ramos Gonzalez - 202414411
//lll
import java.io.*;
import java.util.*;

public class ProblemaP1 {

    static final long INFINITO = Long.MAX_VALUE / 2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(br);

        StringBuilder salida = new StringBuilder();

        st.nextToken();
        int casos = (int) st.nval;

        for (int c = 0; c < casos; c++) {

            st.nextToken(); int n = (int) st.nval;
            st.nextToken(); int m = (int) st.nval;
            st.nextToken(); int p = (int) st.nval;

            int[] e = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                st.nextToken();
                e[i] = (int) st.nval; // costo de moverse dentro de la orbita i
            }

            // agrupamos los portales por su orbita de origen
            // portalesPorOrigen[i] = lista de portales que salen de la orbita i
            List<int[]>[] portalesPorOrigen = new List[n + 1];
            for (int i = 1; i <= n; i++) {
                portalesPorOrigen[i] = new ArrayList<>();
            }

            for (int k = 0; k < p; k++) {
                st.nextToken(); int xs = (int) st.nval;
                st.nextToken(); int ys = (int) st.nval;
                st.nextToken(); int xe = (int) st.nval;
                st.nextToken(); int ye = (int) st.nval;
                portalesPorOrigen[xs].add(new int[]{ys, xe, ye}); // guardamos solo lo que falta
            }

            long costo = calcularCostoMinimo(n, m, e, portalesPorOrigen);

            salida.append(costo == -1 ? "NO EXISTE" : costo).append("\n");
        }

        System.out.print(salida);
    }

    static long calcularCostoMinimo(int n, int m, int[] e, List<int[]>[] portalesPorOrigen) {

        // D[i][j] = costo minimo para llegar desde (1,1) hasta (i,j)
        long[][] D = new long[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                D[i][j] = INFINITO;
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

            // solo revisamos los portales que salen de esta orbita, ya agrupados
            for (int[] portal : portalesPorOrigen[i]) {
                int ys = portal[0];
                int xe = portal[1];
                int ye = portal[2];

                long costoPortal = D[i][ys]; // el portal no suma costo
                if (costoPortal < D[xe][ye]) {
                    D[xe][ye] = costoPortal;
                }
            }
        }

        if (D[n][m] >= INFINITO) {
            return -1;
        }
        return D[n][m];
    }
}