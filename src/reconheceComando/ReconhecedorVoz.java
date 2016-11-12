/**
 *
 * @author Darlei
 */
package reconheceComando;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import javax.swing.JOptionPane;

public class ReconhecedorVoz extends Thread {
    //ReconhecedorThread recorder;

    Microphone microphone;
    ConfigurationManager cm;
    String comandoReconhecido = "0";
    int cont = 0;
    int contPalavras = 0;
    Boolean iniciado = false;
    //Aloca o reconhecedor
    Recognizer recognizer = null;

    public ReconhecedorVoz(String name){
        super(name);
    }
    
    public synchronized String iniciaReconhecimento(Microphone microphone) throws InterruptedException {

        // Inicia o microfone ou fecha se nao for possivel
        // Microphone microphone = (Microphone) cm.lookup("microphone");

        if (!iniciado){
           // Carrega o arquivo de configuracoes
           cm = new ConfigurationManager(ReconhecedorVoz.class.getResource("ReconheceComando.config.xml"));

           //Aloca o reconhecedor
           recognizer = (Recognizer) cm.lookup("recognizer");

           recognizer.allocate();

           microphone = (Microphone) cm.lookup("microphone");

            if (!microphone.startRecording()) {
                JOptionPane.showMessageDialog(null, "Nao foi possivel inicar o microfone!");
                recognizer.deallocate();
                System.exit(1);
            }
            iniciado = true;
            System.out.println("passou uma vez pela alocacao de recursos");
        }
        else {
            //Aloca o reconhecedor
            recognizer = (Recognizer) cm.lookup("recognizer");
            microphone = (Microphone) cm.lookup("microphone");
            microphone.initialize();
            microphone.startRecording();
        }

        System.out.println("Aguardando comando de voz...");
        // loop de reconhecimento, aguardando comando de voz do usuario
        while (true){
            Result result = recognizer.recognize();
            
            if (result != null) {
                comandoReconhecido = result.getBestFinalResultNoFiller();
                
                if (!comandoReconhecido.isEmpty()) {
                    System.out.println("Comando dito: " + comandoReconhecido);
                    
                    if (comandoReconhecido.equalsIgnoreCase("one music")) {
                        microphone.stopRecording();
                        System.out.println("Interrompeu microfone");       
                        return comandoReconhecido;
                    } 
                    else if (comandoReconhecido.equalsIgnoreCase("two music")) {
                        microphone.stopRecording();
                        System.out.println("Interrompeu microfone");       
                        return comandoReconhecido;
                    }
                    else if (comandoReconhecido.equalsIgnoreCase("music")) {
                        microphone.stopRecording();
                        System.out.println("Interrompeu microfone");       
                        return comandoReconhecido;
                    }
                    else if (comandoReconhecido.equalsIgnoreCase("next")) {
                        microphone.stopRecording();
                        System.out.println("Interrompeu microfone");
                        return comandoReconhecido;
                    }
                    else if (comandoReconhecido.equalsIgnoreCase("break")) {  
                        microphone.stopRecording();
                        System.out.println("Interrompeu microfone");
                        return comandoReconhecido;
                    }
                }
                else if (comandoReconhecido.isEmpty()) {
                    cont++;
                    if (cont > 3) {
                        System.out.println("Comando não reconhecido");
                        cont = 0;
                        microphone.stopRecording();
                        System.out.println("Interrompeu microfone");
                        return "ComandoNaoReconhecido";
                    }
                }
            }
        }
    }
}