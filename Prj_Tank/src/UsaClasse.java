import java.util.Random;
import java.util.Scanner;

public class UsaClasse {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Pergunta quantos tanques/jogadores
        System.out.print("Digite a quantidade de jogadores/tanques: ");
        int qtd = scanner.nextInt();
        scanner.nextLine(); // limpar buffer

        // Cria os tanques com nome digitado
        Tank[] tanques = new Tank[qtd];
        for (int i = 0; i < qtd; i++) {
            System.out.print("Digite o nome do tanque " + (i + 1) + ": ");
            String nome = scanner.nextLine();
            tanques[i] = new Tank(nome);
        }

        System.out.println("\n--- INÍCIO DO JOGO ---");

        boolean jogoAtivo = true;
        int rodada = 1;

        while (jogoAtivo) {
            System.out.println("\n===== Rodada " + rodada + " =====");

            // Sorteia um tanque atacante
            int atacanteIndex = random.nextInt(tanques.length);
            Tank atacante = tanques[atacanteIndex];

            // Se o tanque sorteado está morto, pula rodada
            if (!atacante.isAlive()) {
                System.out.println(atacante.getName() + " está destruído e não pode atacar.");
                rodada++;
                continue;
            }

            // Mostra possíveis alvos
            System.out.println("\n" + atacante.getName() + " foi sorteado para atacar!");
            System.out.println("Escolha um alvo:");
            for (int i = 0; i < tanques.length; i++) {
                if (i != atacanteIndex && tanques[i].isAlive()) {
                    System.out.println(i + " - " + tanques[i]);
                }
            }

            // Jogador escolhe alvo
            int alvoIndex;
            do {
                System.out.print("Digite o número do tanque alvo: ");
                alvoIndex = scanner.nextInt();
            } while (alvoIndex == atacanteIndex || !tanques[alvoIndex].isAlive());

            Tank alvo = tanques[alvoIndex];

            // Realiza ataque
            atacante.fire_at(alvo);

            // Mostra status de todos os tanques
            System.out.println("\n--- Status após rodada ---");
            for (Tank t : tanques) {
                System.out.println(t);
            }

            // Verifica quantos tanques ainda estão vivos
            int vivos = 0;
            Tank ultimoVivo = null;
            for (Tank t : tanques) {
                if (t.isAlive()) {
                    vivos++;
                    ultimoVivo = t;
                }
            }

            if (vivos <= 1) {
                jogoAtivo = false;
                System.out.println("\n=== GAME OVER ===");
                if (ultimoVivo != null) {
                    System.out.println("🏆 O vencedor foi: " + ultimoVivo.getName() + " 🏆");
                } else {
                    System.out.println("Todos os tanques foram destruídos!");
                }
            }

            rodada++;
        }

        scanner.close();
    }
}