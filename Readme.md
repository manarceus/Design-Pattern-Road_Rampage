# 🚗 Road Rampage

## 🎯 Objectif du jeu
Contrôlez un personnage qui saute de voiture en voiture pour éviter les collisions et **maximiser votre score**.

---

## 🎮 Contrôles

- **A / D** ou **← →** : Changer de voie
- **SPACE** : Sauter
- **1** : SpeedBoost *(3 utilisations)*
- **2** : Bouclier *(3 utilisations, durée 5s)*
- **ESC** : Pause
- **ENTER** : Menu

### ❤️ Vie (HP)
- **3 collisions = Game Over**
- Le **bouclier absorbe 1 collision**

---

## ⚙️ Design Patterns Implémentés

### 1. 🧩 State Pattern
- **États du jeu** :  
  `Menu → Playing → Pause → GameOver`
- **États du joueur** :  
  `OnCar → Jumping → Falling → Dead`

---

### 2. ⚡ Decorator Pattern
Power-ups temporaires *(5 secondes, max 3 utilisations)* :
- **SpeedBoost** : Vitesse ×1.8
- **Shield** : Bloque 1 collision

---

### 3. 🧱 Composite Pattern
Hiérarchie du monde :
Level → Road → Lane → Car

---

### 4. 🧠 Strategy Pattern
Comportements des voitures *(par couleur)* :
- 🔴 **Rouge** : Aggressive *(rapide, change de voie)*
- 🟢 **Vert** : Cautious *(lent, ligne droite)*
- 🟠 **Orange** : Random *(imprévisible)*

---

### 5. 👁 Observer Pattern
- **Game (Subject)** → `DebugObserver`
- Notifications :
    - `COLLISION`
    - `POWERUP_USED`
    - `PLAYER_HIT`
    - `SCORE_CHANGED`

---

### 6. 🪪 Singleton Pattern
- **GameLogger**
- Trace tous les événements dans :  
  `logs/road-rampage.log`

---

## 📁 Structure du Projet
src/main/java/com/road/rampage/

├── core/ # Singleton + Observer

├── state/ # State Pattern

├── player/ # PlayerState

├── character/ # Decorator

├── powerup/ # TimedDecorator

├── world/ # Composite + Strategy

└── Main.java

