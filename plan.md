# fix me plan

## Student context & agent interaction rules

**School**: Codam (42 network), Amsterdam.

**AI policy at Codam**: AI tools are allowed as a learning aid — asking questions, exploring concepts, comparing approaches. What is explicitly forbidden is copy-pasting AI-generated code you don't understand into your project. The student must be able to understand and explain every single line they submit. AI is a tutor/peer, not a substitute developer.

**Instructions for agents**: Do NOT just write code and hand it over. For every decision or implementation:

- The student wants to write their own code. Don't write code for them, just give examples/suggestions.
- Explain **what** you are suggesting and **why** (the design reasoning, not just syntax)
- When there are multiple valid approaches, explain them all: benefits and downsides. The student decides which one to implement.
- If the student asks "why" about anything, always answer — never skip the explanation
- Prefer teaching the pattern once and then helping apply it, over repeating full implementations
- If something is a Java best practice or convention, name it so the student can look it up
- Student has ADHD and prefers small, managable tasks. Also be kind please. We love you.

The goal is that after this project the student can sit in front of a peer evaluator and defend every line of code in the codebase, because the student wrote everything themselves.

---

## Requirements (from subject — ALL must be met)

### Build / project structure

- Multi-module Maven build; root `mvn clean package` must produce a runnable `.jar` per component.
- No default package; own packages, Java naming conventions.
- Don't commit `.class` files (`.gitignore` target/).
- Java up to latest LTS. Any external libraries/build tools/code generators allowed.

### Architecture (Objectives)

- 3 independent components: Router (server), Broker (client), Market (client), communicating over TCP.
- Non-blocking sockets required.
- Java executor framework required for message handling.
- Clean, readable, peer-understandable design; easy to change.
- Hint given in subject: Chain-of-Responsibility pattern.

### Router (V.1)

- Central hub; performs NO business logic — only dispatches messages.
- Accepts multiple concurrent Broker + Market connections.
- Port 5000 = Brokers, Port 5001 = Markets.
- On connect: assign a unique 6-digit ID, send it to the connecting component.
- Every message includes sender's ID (Router uses it to build the routing table).
- Per incoming message: 1) validate checksum, 2) look up destination in routing table, 3) forward.

### Broker (V.2)

- Sends: Buy, Sell orders.
- Receives: Executed, Rejected.

### Market (V.3)

- Holds a list of tradable instruments (+ quantity).
- On order: try to execute → update instrument list + send Executed, OR send Rejected.
- Minimum required rules: reject if instrument not traded there; reject Buy if quantity unavailable.
- Rest of the matching/execution logic is the student's own "simulator" design space.

### FIX Messages (V.4)

- Must follow FIX-style notation (simplified, not the real spec).
- Every message: starts with router-assigned ID, ends with checksum.
- Buy/Sell mandatory fields: Instrument, Quantity, Market, Price.

### Bonus (only assessed if mandatory part is 100% perfect)

- Store all transactions in a database.
- Fail-over mechanism: restore ongoing transactions if a component crashes.

### Submission

- Everything must live in the Git repo; double-check folder/file names before defense.

---

## Global step plan

A. **Scaffolding** — ✅ DONE. Modules: `fixme-core` (no `common`), `fixme-router`, `fixme-broker`, `fixme-market`. `mvn clean package` gives a runnable jar per component.
B. **Shared message protocol** — define the FIX-style message format (fields, checksum, message boundaries) that Router/Broker/Market will all use to talk to each other.
C. **Shared networking layer** — non-blocking socket handling + executor-based message processing, built once and reused by all three components.
D. **Router** — accept Broker connections (port 5000) and Market connections (port 5001), assign unique 6-digit IDs, maintain a routing table, implement the validate → lookup → forward pipeline.
E. **Market** — instrument book + order execution rules (must at least reject unknown instruments, and reject Buy orders when quantity is unavailable).
F. **Broker** — produce Buy/Sell orders and handle Executed/Rejected replies.
G. **Integration & verification** — run all components together end-to-end; check edge cases (bad checksum, unknown destination, disconnects); check behavior under multiple simultaneous connections.
H. **Bonus** (only once mandatory is fully working — subject: bonus isn't graded otherwise) — transaction persistence to a database + failover/recovery mechanism.

(E and F don't depend on each other, but both need C and D to exist first.)

**Decisions to make when you actually reach them (not upfront):**

1. How to implement "non-blocking sockets" in practice, and how that pairs with the executor framework for message handling.
2. How to frame messages on the wire, so a receiver knows where one message ends and the next begins (TCP is just a byte stream — no built-in message boundaries).
3. Exact field set for messages beyond what the subject mandates (e.g. what Executed/Rejected need to carry so they can be routed back to the right Broker).
4. How Broker produces orders (manual input, generator, script...).
5. What Router does on an invalid checksum or unknown destination.
6. Whether to use any external libraries for the networking/protocol core, or stick to plain Java.
