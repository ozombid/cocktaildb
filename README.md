# cocktaildb

## binome : 
  - Oussama ZOBID
  - Salim BEKKARI

## Fonctionnalités développées

### Tab Bar : Recherche, Catégories et Ingrédients

• Premier onglet : Recherche : permets de rechercher sur une recette par son nom, la recherche est synchronisé avec la saisie (pas besoin de cliquer sur entrer pour envoyer la requete du recherche), en cliquant sur une recette, cela genere les detals de cette recette (Categorie, Instructions, Ingredients...)

• Deuxième onglet : Catégories : liste tout les catégories de l'API, et génère la liste des recettes associées à une categorie selectionée. En cliquant sur une recette spécifique, cela génère ses détails.

•Troisième onglet : Ingrédients : liste tout les ingrédents de l'API, et génère la liste des recettes associées à un ingrédient selectioné. En cliquant sur une recette spécifique, cela génère ses détails.

## Difficultés rencontrées

Nous avons été confrontés à un dilemme lors du choix de la structure de notre projet : opter pour un découpage de paquetage par entité (Catégories, Ingrédients, etc.) ou par type d'outil (fragments, adaptateurs, activités...). Finalement, nous avons opté pour le découpage par entité. Cette décision a été motivée par le fait que certaines entités nécessitent des outils différents, parfois inédits. Il ne semblait pas judicieux de créer des paquetages spécifiques à un outil pour une seule entité, d'où notre approche orientée vers le découpage par entités.

En ce qui concerne le débogage, nous avons rencontré plusieurs difficultés. Pour les surmonter, nous avons fait appel à l'outil Logcat, qui nous a permis de suivre l'exécution de l'application en temps réel et de localiser précisément les erreurs.

Nous avons également fait face à des problèmes d'incompatibilité de versions entre Android, Kotlin, et même le compilateur. Pour résoudre ce problème, nous avons opté pour une version moins récente, Android 7.4.2, afin d'assurer la compatibilité.

Notre équipe a également dû relever des défis de synchronisation, notamment en ce qui concerne la duplication des fichiers adaptateurs. Bien que la fusion et la rationalisation du code des adaptateurs soient des solutions courantes pour améliorer la collaboration et la maintenance du code, nous avons choisi de ne pas adopter cette approche. Afin de maintenir une conception orientée objet et de préserver la généralité du code, chaque entité dispose de son propre adaptateur, même s'ils sont similaires. Cette stratégie nous permet de modifier un adaptateur spécifique ultérieurement sans impacter les autres., meme s'il sont similaires), comme cela si on doit changr ulterieurement un adapteur, il suffit de modifier directement.



