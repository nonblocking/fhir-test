import React from 'react';
import Patient from "./Patient";

export default () => (
    <div className="m-2">
        <h1 className="text-4xl text-blue-700">FHIR Test</h1>
        <Patient patientId="1" />
    </div>
);
