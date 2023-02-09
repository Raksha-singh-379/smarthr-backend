import dayjs from 'dayjs/esm';

import { IReportingStructure, NewReportingStructure } from './reporting-structure.model';

export const sampleWithRequiredData: IReportingStructure = {
  id: 47386,
};

export const sampleWithPartialData: IReportingStructure = {
  id: 7688,
  employeeId: 71496,
  status: 'Business-focused drive green',
  reportingStrId: 81315,
  companyId: 80253,
};

export const sampleWithFullData: IReportingStructure = {
  id: 13101,
  employeeId: 97616,
  reportingEmpId: 22856,
  status: 'Rustic Metal Shoes',
  reportingStrId: 54843,
  lastModified: dayjs('2023-02-08T11:13'),
  lastModifiedBy: 'Licensed orchestrate feed',
  companyId: 80299,
};

export const sampleWithNewData: NewReportingStructure = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
