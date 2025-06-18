import React, {useEffect, useState} from 'react';
import type {Patient} from '../server/types';

type Props = {
    patientId: string;
}

export default ({patientId}: Props) => {
    const [error, setError] = useState(false);
    const [patient, setPatient] = useState<Patient | null>(null);

    useEffect(() => {
        setError(false);
        const load = async () => {
            try {
                const response = await fetch(`/api/patients/${patientId}`);
                setPatient(await response.json());
            } catch (e) {
                console.error(e);
                setError(true);
            }
        };
        load();
    }, [patientId]);

    return (
        <div className="mt-2">
            <h2 className="text-2xl">Patent {patientId}</h2>
            {error && (
                <div className="text-red-800">Error loading patient data!</div>
            )}
            {!error && !patient && (
                <div>Loading...</div>
            )}
            {patient && (
                <>
                    <div className="flex mt-1">
                        <div>Given Name:</div>
                        <div className="ml-1">{patient.givenName}</div>
                    </div>
                    <div className="flex mt-1">
                        <div>Family Name:</div>
                        <div className="ml-1">{patient.familyName}</div>
                    </div>
                </>
            )}
        </div>
    )
}
