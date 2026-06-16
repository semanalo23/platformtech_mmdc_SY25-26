const express = require('express');
const cors = require('cors');
const app = express();
const PORT = 5000;

app.use(cors());
app.use(express.json());

// --- SIMULATED IN-MEMORY DATABASE & REPLICA ---
let primaryDatabase = [];
let readReplica = [];

// --- API GATEWAY ROUTER ---
app.post('/api/checkout', (req, res) => {
    console.log("\n[API GATEWAY] Traffic received at /api/checkout. Clearing security...");
    console.log("[LIFECYCLE] Spawning a fresh CartAndCheckoutServices instance in memory...");
    
    const transactionData = {
        id: Date.now(),
        amount: "P15,500.00",
        status: "SUCCESS",
        timestamp: new Date().toLocaleTimeString()
    };

    // Write to Primary DB
    primaryDatabase.push(transactionData);
    console.log("[PRIMARY DB] Transaction saved successfully.");

    // Asynchronous Read Replication Simulation
    setTimeout(() => {
        readReplica.push(transactionData);
        console.log("[DB PIPELINE] Data mirrored down to Read Replica Analytics.");
    }, 200);

    res.status(200).json({ message: "Checkout completed successfully!", data: transactionData });
});

app.get('/api/analytics', (req, res) => {
    console.log("[READ REPLICA] BI Service pulling data directly from analytics mirror.");
    res.json(readReplica);
});

app.listen(PORT, () => console.log(`FinMark Backend Architecture running on port ${PORT}`));