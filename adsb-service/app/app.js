const express = require("express");
const app = express();
const port = 5003;
const adsbRoute = require("./routes/adsbRouter");

app.use(express.json());

app.use("/adsbData", adsbRoute);

app.listen(port, () => {
    console.log(`server is listening on port ${port}`);
    console.log(Date.now().toString());
})