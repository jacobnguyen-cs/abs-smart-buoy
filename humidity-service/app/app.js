const express = require("express");
const app = express();
const port = 5000 || 3000;
const humidityRoute = require("./routes/humidityRouter");

app.use(express.json());

app.use("/humidity", humidityRoute);

app.listen(port, () => {
    console.log(`server is listening on port ${port}`);
})