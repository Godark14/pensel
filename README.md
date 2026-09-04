# Pensel

Application Android e-commerce pour la vente de toiles/canvas, pensée nativement pour les appareils pliables (fold).

## Fonctionnalités

- Écran de checkout avec formulaire de livraison, paiement, et récapitulatif de commande
- Support natif fold : bascule automatique entre deux postures
    - **CLOSED** — layout vertical, une seule colonne, récap panier en accordéon repliable
    - **OPENED** — layout deux colonnes (formulaire à gauche, récap toujours visible à droite)
- Thème clair/sombre adaptatif basé sur les préférences système
- Devise en dollar ($)

## Stack technique

- Kotlin 2.2.10
- Jetpack Compose (BOM 2026.02.01)
- AGP 9.4.0 / Gradle 9.6.0
- androidx.window pour la détection de posture fold
- androidx.lifecycle (ViewModel + StateFlow) pour la gestion d'état
- androidx.navigation-compose
- Coil pour le chargement d'images
- compileSdk/targetSdk 37, minSdk 24

## Architecture

- com.godark14.pensel/
    - data/
        - model/ → modèles de données (Product, CartItem, DeliveryInfo, PaymentInfo)
        - mock/ → repository mocké (données de démo, avant intégration API)
    - fold/ → détection de posture fold (CLOSED/OPENED uniquement)
    - ui/
        - checkout/ → écran de checkout et ses sections (livraison, paiement, récap)
        - components/ → composants réutilisables
        - theme/ → thème Pensel (couleurs, typographie, dark/light)
    - MainActivity.kt

## À venir

- Intégration API (Retrofit) pour remplacer les données mockées
- Écrans catalogue et accueil
- Validation de commande complète