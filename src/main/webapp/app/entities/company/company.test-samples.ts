import dayjs from 'dayjs/esm';

import { ICompany, NewCompany } from './company.model';

export const sampleWithRequiredData: ICompany = {
  id: 32440,
};

export const sampleWithPartialData: ICompany = {
  id: 72260,
  contactPerson: 'Movies Borders',
  email: 'Jaclyn21@hotmail.com',
  phoneNumber: 'Hat Devolved',
  mobileNumber: 'Australia',
  fax: 'Technician',
  status: 'Nebraska',
};

export const sampleWithFullData: ICompany = {
  id: 47834,
  companyName: 'panel RAM withdrawal',
  contactPerson: 'JSON payment syndicate',
  postalCode: 'e-business',
  email: 'Ward57@gmail.com',
  phoneNumber: 'back-end Utah models',
  mobileNumber: 'innovative',
  websiteUrl: 'Response multi-byte',
  fax: 'parse',
  regNumber: '24/7 protocol',
  status: 'Market Tasty virtual',
  lastModified: dayjs('2023-02-08T09:21'),
  lastModifiedBy: 'Avon Pine interface',
  leaveSettingLevel: 'Mouse client-driven',
};

export const sampleWithNewData: NewCompany = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
