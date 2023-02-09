import dayjs from 'dayjs/esm';

export interface IWorkinDaysSetting {
  id: number;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  companyId?: number | null;
}

export type NewWorkinDaysSetting = Omit<IWorkinDaysSetting, 'id'> & { id: null };
