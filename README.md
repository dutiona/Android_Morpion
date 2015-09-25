Projet Morpion EISTI I3 2015
Auteur : Micha�l Roynard.
Environnement de d�veloppement : Android Studio 1.3.2.

------------------------------------------------------------
Processus de build :

Simplement ouvrir le projet avec Android Studio, lancer le Make app puis lancer Build project.

Processus d'�x�cution :

J'ai fait tous mes tests sur la machine virtuelle suivante :
Mat�riel : Nexus 5 API 23,
Ecran : 1080x1920: xxhdpi,
API : API 23,
Target : Google APIs,
CPU/ABI : x86,
Size on Disk: 1GB

M�me si la machine a une API 23, le projet compile avec une target API 16.
Avec une API 15 il y aura une erreur de compilation.

-----------------------------------------------------------

Traitement du sujet : faire une application que tu as toujours voulu sur t�l�phone.

Non pas que j'ai toujours voulu avoir un morpion sur mon t�l�phone, mais pour passer le temps, c'est vraiment pratique et passe partout !

-----

Application propos�e : jeu de morpion contre l'ordinateur. Le jeu dispose d'une IA avec plusieurs niveaux de difficult�s.
Personnellement, je ne suis arriv� � battre l'IA impossible qu'une seule fois (et en comman�ant la partie).
Je suis plut�t satisfait du r�sultat !

J'ai opt� pour une UI �pur�e, je trouve que les �crans sombres ab�ment moins les yeux.

La Javadoc a �t� g�n�r�e (pour la re-g�n�rer ne pas oublier les options suivantes : -encoding utf8 -docencoding utf8 -charset utf8).

Les sources sont propres et comment�es.

Concernant le choix d'impl�menter la grille dans une webView, j'ai trouv� que �a me permettait plus de libert�s quant aux animations et aux styles gr�ce � javascript (et c'est plus pratique).
De plus cela s'int�gre tr�s bien � Android (l'interop�rabilit� est saisissante de facilit�).
Du coup je ne suis pas revenu en arri�re et j'ai m�me embarqu� jQuery pour gagner du temps.

J'ai bien pris le temps de paufiner et de tester le projet, il devrait y avoir tr�s peu de bug (voir aucun !).