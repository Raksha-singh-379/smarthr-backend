import dayjs from 'dayjs/esm';

export interface ITds {
  id: number;
  salaryFrom?: dayjs.Dayjs | null;
  salaryTo?: dayjs.Dayjs | null;
  percentage?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  salarySettingId?: number | null;
  companyId?: number | null;
}

export type NewTds = Omit<ITds, 'id'> & { id: null };
