import FHIRClient from 'fhir-kit-client';
import logger from '../logger';
import type { Request, Response } from 'express';
import {Patient} from '../types';

const FHIR_BASE_URL = process.env.FHIR_GATEWAY_URL ?? 'http://localhost:5558/fhir'
const fhirClient = new FHIRClient({ baseUrl: FHIR_BASE_URL })

type Params = {
   id: string;
};

export default async (req: Request<Params>, res: Response) => {
   try {
      const {id = '??', name = []} = await fhirClient.read({resourceType: 'Patient', id: req.params.id}) as fhir.Patient;

      // Map to simplified format for the frontend
      const patient: Patient = {
         id,
         givenName: name[0].given?.[0] ?? '??',
         familyName: name[0].family ?? '??',
      }

      res.json(patient);

   } catch (e) {
      logger.error(e);
      res.sendStatus(500);
   }
};
