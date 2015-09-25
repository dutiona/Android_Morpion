Projet Morpion EISTI I3 2015
Auteur : Michaël Roynard.
Environnement de développement : Android Studio 1.3.2.

------------------------------------------------------------
Processus de build :

Simplement ouvrir le projet avec Android Studio, lancer le Make app puis lancer Build project.

Processus d'éxécution :

J'ai fait tous mes tests sur la machine virtuelle suivante :
Matériel : Nexus 5 API 23,
Ecran : 1080x1920: xxhdpi,
API : API 23,
Target : Google APIs,
CPU/ABI : x86,
Size on Disk: 1GB

Même si la machine a une API 23, le projet compile avec une target API 16.
Avec une API 15 il y aura une erreur de compilation.

-----------------------------------------------------------

Traitement du sujet : faire une application que tu as toujours voulu sur téléphone.

Non pas que j'ai toujours voulu avoir un morpion sur mon téléphone, mais pour passer le temps, c'est vraiment pratique et passe partout !

-----

Application proposée : jeu de morpion contre l'ordinateur. Le jeu dispose d'une IA avec plusieurs niveaux de difficultés.
Personnellement, je ne suis arrivé à battre l'IA impossible qu'une seule fois (et en commançant la partie).
Je suis plutôt satisfait du résultat !

J'ai opté pour une UI épurée, je trouve que les écrans sombres abîment moins les yeux.

La Javadoc a été générée (pour la re-générer ne pas oublier les options suivantes : -encoding utf8 -docencoding utf8 -charset utf8).

Les sources sont propres et commentées.

Concernant le choix d'implémenter la grille dans une webView, j'ai trouvé que ça me permettait plus de libertés quant aux animations et aux styles grâce à javascript (et c'est plus pratique).
De plus cela s'intègre très bien à Android (l'interopérabilité est saisissante de facilité).
Du coup je ne suis pas revenu en arrière et j'ai même embarqué jQuery pour gagner du temps.

J'ai bien pris le temps de paufiner et de tester le projet, il devrait y avoir très peu de bug (voir aucun !).