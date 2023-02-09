import dayjs from 'dayjs/esm';

import { ICompany, NewCompany } from './company.model';

export const sampleWithRequiredData: ICompany = {
  id: 32440,
};

export const sampleWithPartialData: ICompany = {
  id: 1226,
  contactPerson: 'Granite',
  email: 'Mason.Klocko@yahoo.com',
  phoneNumber: 'Estate',
  mobileNumber: 'Devolved',
  fax: 'Australia',
  lastModified: dayjs('2023-02-08T23:23'),
  pan: 'Cayman Nebraska Station',
};

export const sampleWithFullData: ICompany = {
  id: 15421,
  companyName: 'RAM withdrawal',
  contactPerson: 'JSON payment syndicate',
  postalCode: 'e-business',
  email: 'Ward57@gmail.com',
  phoneNumber: 'back-end Utah models',
  mobileNumber: 'innovative',
  websiteUrl: 'Response multi-byte',
  fax: 'parse',
  status: '24/7 protocol',
  lastModified: dayjs('2023-02-08T08:54'),
  lastModifiedBy: 'homogeneous Interactions',
  regNumber: 'Tobago Avon',
  gstin: 'Home Bolivia',
  pan: 'red',
  tan: 'Granite',
};

export const sampleWithNewData: NewCompany = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
