import dayjs from 'dayjs/esm';

import { IReEnumeration, NewReEnumeration } from './re-enumeration.model';

export const sampleWithRequiredData: IReEnumeration = {
  id: 93138,
};

export const sampleWithPartialData: IReEnumeration = {
  id: 20995,
  lastModifiedBy: 'Parks circuit',
  companyId: 81681,
};

export const sampleWithFullData: IReEnumeration = {
  id: 66752,
  salaryBasis: 'extranet',
  amount: 39945,
  paymentType: 'facilitate Chips Future',
  lastModified: dayjs('2023-02-08T11:09'),
  lastModifiedBy: 'Bedfordshire Credit',
  status: 'Diverse',
  employeId: 19045,
  companyId: 76000,
};

export const sampleWithNewData: NewReEnumeration = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
