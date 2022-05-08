"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.api = void 0;
const functions = require("firebase-functions");
const admin = require("firebase-admin");
const express = require("express");
const bodyParser = require("body-parser");
//initialize firebase inorder to access its services
admin.initializeApp(functions.config().firebase);
//initialize express server
const app = express();
const main = express();
//add the path to receive request and set json as bodyParser to process the body
main.use('/v1', app);
main.use(bodyParser.json());
main.use(bodyParser.urlencoded({ extended: false }));
//define google cloud function name
exports.api = functions.https.onRequest(main);
app.get('/', (req, res) => {
    res.status(200).send(`works`);
});
const nodemailer = require("nodemailer");
const gmailEmail = "saleemmater123@gmail.com";
const gmailPassword = "qrzvbuuctbivmcwc";
const mailTransport = nodemailer.createTransport({
    service: "gmail",
    auth: {
        user: gmailEmail,
        pass: gmailPassword,
    },
});
function SendMail(from, email, subject, text, auth) {
    const mailOptions = {
        from: from,
        to: email,
        subject: subject,
        text: text
    };
    // Building Email message.
    try {
        if (auth == "AIzaSyAA2hae3PgHQMd9UeeLArSOXC9pKUFUFMs") {
            mailTransport.sendMail(mailOptions);
            functions.logger.log(`New subscription confirmation email sent to:`, email);
        }
        else {
            functions.logger.log(`Error Auth`, email);
            console.log("Error Auth");
        }
    }
    catch (error) {
        functions.logger.error("There was an error while sending the email:", error);
    }
}
app.post('/send', async (req, res) => {
    try {
        functions.logger.log(req.body);
        SendMail(req.body['from'], req.body['email'], req.body['subject'], req.body['text'], req.body['auth']);
        res.status(200).send();
    }
    catch (error) {
        res.status(400).send(`User should cointain email`);
    }
});
//# sourceMappingURL=index.js.map