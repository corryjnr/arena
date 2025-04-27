# Arena
  ![joystick](https://github.com/user-attachments/assets/b005ee9a-2ebc-492d-8ba3-0ddb7a3ff39c)

## Project: Real-Time Multiplayer Game Server (with Betting Feature)

---

## 1. Introduction

### 1.1 Purpose
The purpose of this document is to define the functional and non-functional requirements for a **Real-Time Multiplayer Game Server** that supports real-time play **and in-game betting between players**. The server will handle real-time communication, synchronization of game state, client management, and betting transactions.

### 1.2 Scope
The system will support:
- Real-time multiplayer gaming.
- Lobby and matchmaking system.
- Player state synchronization.
- Basic in-game rules and event handling.
- **Betting system** where players can wager virtual currency on game outcomes.
- Scalability for high concurrent usage.

---

## 2. Overall Description

### 2.1 Product Perspective
This server acts as the backend engine for multiplayer games with a **competitive wagering** element. Players will be able to place bets on game results (e.g., winner of a match) before a game starts. Winnings are distributed automatically after the game ends.

### 2.2 Product Functions
- Connection Management (WebSocket).
- Lobby Management and Game Sessions.
- Real-Time State Synchronization.
- Event Handling for Player Actions.
- **Betting Management: Place Bets, Validate Bets, Distribute Winnings.**
- Game Loop Management.

### 2.3 User Characteristics
Users are competitive gamers who want both entertainment and competition with stakes, using **virtual currency** (non-real money in first version).

### 2.4 Assumptions and Dependencies
- Betting involves only **virtual coins** (not real money or crypto).
- Future upgrade could introduce real-money systems with compliance considerations.
- Players must be registered/authenticated before placing a bet.
- Java 17+, Spring Boot or Netty stack.

---

## 3. Specific Requirements

### 3.1 Functional Requirements (FR)

| ID  | Requirement |
|-----|-------------|
| FR-01 | The server must allow clients to connect via WebSocket. |
| FR-02 | The server must authenticate new connections. |
| FR-03 | The server must allow players to create and join lobbies. |
| FR-04 | The server must broadcast updated player states at a fixed tick rate. |
| FR-05 | The server must maintain a centralized game loop operating at a fixed tick rate. |
| FR-06 | The server must handle player disconnections and lobby updates. |
| FR-07 | The server must support player-to-player chat within a lobby. |
| FR-08 | The server must enforce game rules and fair play. |
| FR-09 | **The server must allow players to place virtual currency bets on game outcomes before the match starts.** |
| FR-10 | **The server must validate bets (e.g., ensure enough balance, proper bet format).** |
| FR-11 | **The server must lock bets once the game starts. No new bets are allowed mid-game.** |
| FR-12 | **The server must distribute winnings at the end of the match based on game results.** |
| FR-13 | **The server must update player virtual currency balances accordingly.** |
| FR-14 | **The server must provide APIs to query active bets and historical betting results.**

---

### 3.2 Non-Functional Requirements (NFR)

| ID  | Requirement |
|-----|-------------|
| NFR-01 | Max latency of 150ms per message roundtrip under normal load. |
| NFR-02 | Handle 1,000 concurrent WebSocket connections. |
| NFR-03 | Gracefully recover from player disconnections and server crashes. |
| NFR-04 | Log all betting transactions for audit and potential dispute resolution. |
| NFR-05 | Secure all betting data to prevent tampering or cheating. |
| NFR-06 | Codebase must have 80%+ test coverage. |
| NFR-07 | Dockerized deployment setup with horizontal scaling capabilities. |

---

### 3.3 System Models

**High-Level Architecture with Betting Manager:**

```plaintext
Client (WebSocket) <---> Game Server
                          |
                          +-- Lobby Manager
                          +-- Game State Manager
                          +-- Message Serializer/Deserializer
                          +-- Betting Manager
                          +-- Virtual Wallet Service
                          +-- Game Loop Timer
                          +-- Admin API (Health/Monitoring)
```

---

## 4. External Interface Requirements

### 4.1 Client Interface
- WebSocket protocol with JSON/Binary payloads.
- Message Types:
  - CONNECT
  - DISCONNECT
  - JOIN_LOBBY
  - CREATE_LOBBY
  - PLAYER_MOVE
  - PLAYER_ACTION
  - GAME_STATE_UPDATE
  - CHAT_MESSAGE
  - **PLACE_BET**
  - **QUERY_BETS**
  - **BETTING_RESULT**

Example `PLACE_BET` payload:

```json
{
  "type": "PLACE_BET",
  "lobbyId": "abc123",
  "amount": 500,
  "onPlayer": "playerB"
}
```

---

### 4.2 Admin Interface
- REST Endpoints:
  - `/admin/health` — Health check.
  - `/admin/metrics` — Active lobbies, player count, bet volumes.
  - `/admin/bets` — Review bets placed.

---

## 5. Appendices
- The virtual currency system is server-side only — no blockchain in v1.
- Optional Stretch Goals:
  - Leaderboards based on cumulative bet winnings.
  - Anti-collusion mechanisms (detect players teaming up to rig bets).
  - Add a "side bets" system (e.g., "first blood", "most kills").

---
