import {Router} from 'express';
import getPatient from './getPatient';

const api = Router();

api.get('/patients/:id', getPatient);

export default api;
