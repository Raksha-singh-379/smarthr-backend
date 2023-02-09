import dayjs from 'dayjs/esm';

import { IFormValidator, NewFormValidator } from './form-validator.model';

export const sampleWithRequiredData: IFormValidator = {
  id: 37814,
};

export const sampleWithPartialData: IFormValidator = {
  id: 80757,
  formName: 'Security Money withdrawal',
  fieldName: 'Intelligent',
  createdOn: dayjs('2023-02-08T05:43'),
};

export const sampleWithFullData: IFormValidator = {
  id: 20342,
  type: 'Drive',
  value: 'empower quantify parallelism',
  formName: 'pink plum',
  fieldName: 'ivory',
  companyId: 7548,
  createdBy: 'drive',
  createdOn: dayjs('2023-02-08T19:57'),
};

export const sampleWithNewData: NewFormValidator = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
