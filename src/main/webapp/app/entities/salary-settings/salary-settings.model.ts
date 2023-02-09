import dayjs from 'dayjs/esm';

export interface ISalarySettings {
  id: number;
  da?: number | null;
  hra?: number | null;
  employeeShare?: number | null;
  companyShare?: number | null;
  salaryFrom?: dayjs.Dayjs | null;
  salaryTo?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  companyId?: number | null;
}

export type NewSalarySettings = Omit<ISalarySettings, 'id'> & { id: null };
