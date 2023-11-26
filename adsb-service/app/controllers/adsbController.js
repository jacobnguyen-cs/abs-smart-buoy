const { TimestreamWriteClient, WriteRecordsCommand } = require("@aws-sdk/client-timestream-write")
const writeClient = new TimestreamWriteClient({
    region: "us-east-2",
    credentials: {
        accessKeyId: "Insert access key here",
        secretAccessKey: "Insert secret access key here"
    }
});

/**
 * Stores water temperature in the time series database
 * @async
 * @function
 * @param {decimal} value 
 * @param {Time} time 
 * @returns {Promise<object>} A promise object containing the water temperature data
 */
async function storeADSBData(lat, long, time) {
    const DATABASE_NAME = "ADSBDatabase"
    const TABLE_NAME = "ADSBTable"
    console.log("Writing records");
    const dimensions = [
        { 'Name': 'region', 'Value': 'us-east-2' },
        { 'Name': 'az', 'Value': 'az1' },
        { 'Name': 'hostname', 'Value': 'host1' }
    ];
    const data_point = {
        'Dimensions': dimensions,
        'MeasureName': "adsb_data",
        'MeasureValues': [{ 'Name': 'adsb_longitude', 'Type': "DOUBLE", 'Value': long.toString() }, { 'Name': 'adsb_latitude', 'Type': 'DOUBLE', 'Value': lat.toString() }],
        'MeasureValueType': 'MULTI',
        'Time': time.toString()
    }
    const records = [data_point];
    const params = {
        DatabaseName: DATABASE_NAME,
        TableName: TABLE_NAME,
        Records: records
    };
    const command = new WriteRecordsCommand(params);
    const result = await writeClient.send(command);
    return result;
}

module.exports = {
    storeADSBData
};