# VoicePlayer
Reconhecimento de voz com Java e Sphinx-4 Framework

Este projeto é apenas um protótipo e já possui alguns anos (2010).

<b>Antes de executar o projeto, são necessários alguns ajutes:</b>
* Adicione os arquivos .jar (os quais estão no diretório _"lib"_) para o projeto, como bibliotecas.
* Em _"/src/reconheceComando/ReconheceComando.config"_, substitua "{PLACEHOLDER_PATH}" pelo caminho completo. Algo como isso: _"file:/home/user/Projects/VoicePlayer/src/reconheceComando"_. Eu ainda não corrigi esse problema.

O projeto reproduz somente alguns formatos nativos de áudio, suportados pelo java nativamente, como .wav e .au, por exemplo.

Tudo funcionando, quando aberto o player, podem ser adicionados os áudios para reprodução. Além do botão _"Reconhece"_, o reconhecimento de voz pode ser ativado pela tecla _"Ctrl"_ do teclado.

Os comandos de voz aceitos são: _music_, _one music_, _two music_, _next_ e _break_.
Outros comandos pode ser configurados no arquivo _"/src/reconheceComando/ReconheceComando.gram"_.
