import dayjs from 'dayjs/esm';

export interface IReportingStructure {
  id: number;
  employeeId?: number | null;
  reportingEmpId?: number | null;
  status?: string | null;
  reportingStrId?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  companyId?: number | null;
}

export type NewReportingStructure = Omit<IReportingStructure, 'id'> & { id: null };
