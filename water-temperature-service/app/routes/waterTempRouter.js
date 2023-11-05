const express = require("express");
const router = express.Router();

const waterTempController = require("../controllers/waterTempController");

// router.get("/", async (req, res) => {
//     try {
//         const db = req.app.locals.db;
//         var result = await waterTempController.getAllWaterTemp(db);
//         result = {"type": "water-temperature", "data": result};
//         res.json(result);
//     } catch (err) {
//         console.error(err);
//         res.sendStatus(500);
//     }
// });

// router.get("/:id", async (req, res) => {
//     try {
//         const db = req.app.locals.db;
//         const id = req.params.id;
//         const result = await waterTempController.getWaterTemp(db, id);
//         res.json(result);
//     } catch (err) {
//         console.error(err);
//         res.sendStatus(500);
//     }
// });

// router.delete("/:id", async (req, res) => {
//     try {
//         const db = req.app.locals.db;
//         const id = req.params.id;
//         const result = await waterTempController.deleteWaterTemp(db, id);
//         res.json(result);
//     } catch (err) {
//         console.error(err);
//         res.sendStatus(500);
//     }
// });

router.post("/add", async (req, res) => {
    try {
        const value = req.body.temp
        const time = req.body.time
        const result = await waterTempController.storeWaterTemp(value, time)
        res.json(result)
    } catch (err) {
        console.error(err);
        res.sendStatus(500);
    }
});



module.exports = router;